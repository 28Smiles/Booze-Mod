package de.booze.blocks.tiles;

import java.io.IOException;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.IC2;
import ic2.core.ITickCallback;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.core.util.fluid.FluidTankAdv;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.booze.BoozeMod;
import de.booze.BoozeProps;
import de.booze.handlers.BoozeConfigHandler;
import de.booze.handlers.CraftingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileFruidGrinder extends TileBase implements IInventory, IEnergyReceiver, IEnergySink, IFluidHandler, ISidedInventory {
	
	public enum Slots {
		CHARGE, INPUT, OUTPUT
	}
	
	public ItemStack[] inventory = new ItemStack[3];
	protected EnergyStorage energyStorage = new EnergyStorage(10000);
	private boolean addedToEnergyNet;
	protected FluidTankAdv tank = new FluidTankAdv(10000);
	private boolean loaded;
	
	private int workprocess = 0;
	private int workprocess_max = 160;
	
	private ItemStack currentWork = null;
	private ItemStack output = null;
	private FluidStack outputf = null;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!this.addedToEnergyNet) onLoaded();
		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER)) {
		if(inventory[Slots.CHARGE.ordinal()] != null)
			if(inventory[Slots.CHARGE.ordinal()].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem battery = (IEnergyContainerItem)inventory[Slots.CHARGE.ordinal()].getItem();
				
				int e = battery.extractEnergy(inventory[Slots.CHARGE.ordinal()], energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored(), false);
				energyStorage.receiveEnergy(e, false);
			}
		if(currentWork != null && workprocess < workprocess_max) {
			workprocess++;
			energyStorage.extractEnergy(1, false);
		}
		if(currentWork != null && workprocess >= workprocess_max) {
			if(inventory[Slots.OUTPUT.ordinal()] != null) {
				inventory[Slots.OUTPUT.ordinal()].stackSize += output.stackSize;
				tank.fill(outputf, true);
			} else {
				inventory[Slots.OUTPUT.ordinal()] = output.copy();
				tank.fill(outputf, true);
			}
			currentWork = null;
			output = null;
			outputf = null;
			workprocess = 0;
		}
		if(inventory[Slots.INPUT.ordinal()] != null && currentWork == null) {
			Object[] recipe = CraftingHandler.checkFruidGrindRecipes(inventory[Slots.INPUT.ordinal()]);
			if(recipe != null) {
				ItemStack input = (ItemStack) recipe[0];
				ItemStack output = (ItemStack) recipe[1];
				FluidStack outputf = (FluidStack) recipe[2];
				if(tank.getFluid() == null || tank.getCapacity() - tank.getFluidAmount() >= outputf.amount && tank.getFluid().getFluid().equals(outputf.getFluid()))
				if(inventory[Slots.OUTPUT.ordinal()] == null || output.getItem().equals(inventory[Slots.OUTPUT.ordinal()].getItem()) && output.getItemDamage() == inventory[Slots.OUTPUT.ordinal()].getItemDamage() && output.getMaxStackSize() >= (output.stackSize + inventory[Slots.OUTPUT.ordinal()].stackSize))
				if(inventory[Slots.INPUT.ordinal()].stackSize == input.stackSize) {
					inventory[Slots.INPUT.ordinal()] = null;
					currentWork = input.copy();
					this.output = output.copy();
					this.outputf = outputf.copy();
				} else if(inventory[Slots.INPUT.ordinal()].stackSize > input.stackSize) {
					inventory[Slots.INPUT.ordinal()].stackSize -= input.stackSize;
					currentWork = input.copy();
					this.output = output.copy();
					this.outputf = outputf.copy();
				}
				workprocess_max = (Integer) recipe[3];
				workprocess = 0;
			}
		}
		}
	}
	
	public void sendGuiNetworkData(Container paramContainer, ICrafting paramICrafting)
	{
		if ((paramICrafting instanceof EntityPlayer)) {
			PacketCoFHBase localPacketCoFHBase = getGuiPacket();
			if (localPacketCoFHBase != null)
				PacketHandler.sendTo(localPacketCoFHBase, (EntityPlayer)paramICrafting);
		}
	}
	
	@Override
	public PacketCoFHBase getGuiPacket() {
		PacketCoFHBase packet = super.getGuiPacket();
		packet.addFluidStack(tank.getFluid());
		packet.addInt(inventory.length);
		for(ItemStack itemstack : inventory) {
			if(itemstack == null) {
				packet.addItemStack(new ItemStack(Blocks.air, 0));
			} else {
				packet.addItemStack(itemstack);
			}
		}
		packet.addInt(energyStorage.getEnergyStored());
		packet.addInt(workprocess);
		return packet;
	}
	
	@Override
	protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
		super.handleGuiPacket(paramPacketCoFHBase);
		tank.setFluid(paramPacketCoFHBase.getFluidStack());
		int inventory_size = paramPacketCoFHBase.getInt();
		for(int i = 0; i < inventory_size; i++) {
			ItemStack itemstack = paramPacketCoFHBase.getItemStack();
			if(itemstack.stackSize != 0) {
				inventory[i] = itemstack;
			} else {
				inventory[i] = null;
			}
		}
		energyStorage.setEnergyStored(paramPacketCoFHBase.getInt());
		workprocess = paramPacketCoFHBase.getInt();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
		super.writeToNBT(paramNBTTagCompound);
		NBTTagCompound nbt = new NBTTagCompound();
		
		NBTTagCompound nbtTank = new NBTTagCompound();
		tank.writeToNBT(nbtTank);
		nbt.setTag("tank", nbtTank);
		
		NBTTagCompound nbtInventory = new NBTTagCompound();
		nbtInventory.setInteger("inventory_size", inventory.length);
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] != null) {
				NBTTagCompound slot = new NBTTagCompound();
				inventory[i].writeToNBT(slot);
				nbtInventory.setTag("slot_" + i, slot);
			}
		}
		nbt.setTag("inventory", nbtInventory);
		
		NBTTagCompound nbtEnergy = new NBTTagCompound();
		energyStorage.writeToNBT(nbtEnergy);
		nbt.setTag("energy", nbtEnergy);
		
		nbt.setBoolean("addedToEnergyNet", addedToEnergyNet);
		nbt.setBoolean("loaded", loaded);
		
		paramNBTTagCompound.setTag("tile_data", nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
		super.readFromNBT(paramNBTTagCompound);
		NBTTagCompound nbt = (NBTTagCompound) paramNBTTagCompound.getTag("tile_data");
		
		addedToEnergyNet = nbt.getBoolean("addedToEnergyNet");
		loaded = nbt.getBoolean("loaded");
		
		NBTTagCompound nbtEnergy = (NBTTagCompound) nbt.getTag("energy");
		energyStorage.readFromNBT(nbtEnergy);
		
		NBTTagCompound nbtInventory = new NBTTagCompound();
		int i = nbtInventory.getInteger("inventory_size");
		for(int j = 0; j < i; j++) {
			if(nbtInventory.hasKey("slot_" + j)) {
				NBTTagCompound nbtSlot = (NBTTagCompound) nbtInventory.getTag("slot_" + j);
				inventory[j] = ItemStack.loadItemStackFromNBT(nbtSlot);
			} else {
				inventory[i] = null;
			}
		}
		
		NBTTagCompound nbtTank = (NBTTagCompound) nbt.getTag("tank");
		tank.readFromNBT(nbtTank);
	}
	
	@Override
	public boolean openGui(EntityPlayer paramEntityPlayer) {
		if (hasGui()) {
			paramEntityPlayer.openGui(BoozeMod.INSTANCE, BoozeProps.GuiID.FRUIDGRINDER.ordinal(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			return true;
		}
		return false;
	}

	@Override
	public void closeInventory() {}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.inventory[i] == null) {
			return null;
		}
		if (this.inventory[i].stackSize <= j) {
			j = this.inventory[i].stackSize;
		}
		ItemStack localItemStack = this.inventory[i].splitStack(j);
		
		if (this.inventory[i].stackSize <= 0) {
			this.inventory[i] = null;
		}
		return localItemStack;
	}

	@Override
	public String getInventoryName() {
		return tileName;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.inventory[i] == null) {
			return null;
		}
		ItemStack localItemStack = this.inventory[i];
		this.inventory[i] = null;
		return localItemStack;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return !this.tileName.isEmpty();
	}

	@Override
	public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer arg0) {
		return isUseable(arg0);
	}

	@Override
	public void openInventory() {}

	@Override
	public void setInventorySlotContents(int paramInt, ItemStack paramItemStack) {
		this.inventory[paramInt] = paramItemStack;

	    if ((paramItemStack != null) && (paramItemStack.stackSize > getInventoryStackLimit())) {
	      paramItemStack.stackSize = getInventoryStackLimit();
	    }
	    markChunkDirty();
	}

	protected boolean hasEnergy(int paramInt)
	{
		return this.energyStorage.getEnergyStored() >= paramInt;
	}

	protected boolean drainEnergy(int paramInt)
	{
		return (hasEnergy(paramInt)) && (this.energyStorage.extractEnergy(paramInt, false) == paramInt);
	}

	protected void chargeEnergy()
	{
		int i = getChargeSlot();

	    if ((hasChargeSlot()) && (EnergyHelper.isEnergyContainerItem(this.inventory[i]))) {
	      int j = Math.min(this.energyStorage.getMaxReceive(), this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored());
	      this.energyStorage.receiveEnergy(((IEnergyContainerItem)this.inventory[i].getItem()).extractEnergy(this.inventory[i], j, false), false);

	      if (this.inventory[i].stackSize <= 0)
	        this.inventory[i] = null;
	    }
	  }

	private boolean hasChargeSlot() {
		return true;
	}

	public int getChargeSlot() {
		return Slots.CHARGE.ordinal();
	}

	public final void setEnergyStored(int paramInt)
	{
		this.energyStorage.setEnergyStored(paramInt);
	}

	public IEnergyStorage getEnergyStorage()
	{
		return this.energyStorage;
	}

	public int getScaledEnergyStored(int paramInt)
	{
		return MathHelper.round(this.energyStorage.getEnergyStored() * paramInt / this.energyStorage.getMaxEnergyStored());
	}
	
	public int getScaledProcess(int paramInt)
	{
		return MathHelper.round(this.workprocess * paramInt / this.workprocess_max);
	}

	//BC & TE
	public int receiveEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean)
	{
		return this.energyStorage.receiveEnergy(paramInt, paramBoolean);
	}

	public int getEnergyStored(ForgeDirection paramForgeDirection)
	{
		return this.energyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored(ForgeDirection paramForgeDirection)
	{
		return this.energyStorage.getMaxEnergyStored();
	}

	public boolean canConnectEnergy(ForgeDirection paramForgeDirection)
	{
		return this.energyStorage.getMaxEnergyStored() > 0;
	}

	//IC2
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return this.energyStorage.getMaxEnergyStored() > 0;
	}

	@Override
	public double getDemandedEnergy() {
		return (energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored()) / BoozeProps.EU_MJ_Ratio;
	}

	@Override
	public int getSinkTier() {
		return 1;
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		this.energyStorage.receiveEnergy((int) (amount * BoozeProps.EU_MJ_Ratio), false);
		return 0;
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		if (this.loaded) onUnloaded();
	}
	
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (this.loaded) onUnloaded();
	}
	
	public void validate()
	{
		super.validate();
		
		IC2.tickHandler.addSingleTickCallback(this.worldObj, new ITickCallback() {
				
			public void tickCallback(World world) {
				if ((TileFruidGrinder.this.isInvalid()) || (!world.blockExists(TileFruidGrinder.this.xCoord, TileFruidGrinder.this.yCoord, TileFruidGrinder.this.zCoord))) return;
				
				TileFruidGrinder.this.onLoaded();
				
				if ((!TileFruidGrinder.this.isInvalid()) && (TileFruidGrinder.this.enableUpdateEntity()))
					world.loadedTileEntityList.add(TileFruidGrinder.this);
			}
		});
	}
	
	protected boolean enableUpdateEntity() {
		return IC2.platform.isSimulating();
	}

	public void onLoaded()
	{
		if (IC2.platform.isSimulating()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			this.addedToEnergyNet = true;
		}
	}

	public void onUnloaded()
	{
		if ((IC2.platform.isSimulating()) && (this.addedToEnergyNet)) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			this.addedToEnergyNet = false;
		}
	}

	public IFluidTank getTank() {
		return tank;
	}

	@Override
	public boolean canDrain(ForgeDirection arg0, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canFill(ForgeDirection arg0, Fluid arg1) {
		return false;
	}

	@Override
	public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
		return tank.drain(arg1, arg2);
	}

	@Override
	public FluidStack drain(ForgeDirection arg0, int arg1, boolean arg2) {
		return tank.drain(arg1, arg2);
	}

	@Override
	public int fill(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
		return 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
		return new FluidTankInfo[]{ tank.getInfo() };
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int arg2) {
		if(slot == Slots.INPUT.ordinal())
			return false;
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int arg2) {
		if(slot == Slots.OUTPUT.ordinal())
			return false;
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { Slots.INPUT.ordinal(), Slots.OUTPUT.ordinal()};
	}
}

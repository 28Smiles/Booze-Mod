package de.booze.blocks.tiles;

import cofh.api.tileentity.ISidedTexture;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.core.util.fluid.FluidTankAdv;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.BoozeMod;
import de.booze.BoozeProps;
import de.booze.api.data.BlockMeta;
import de.booze.api.data.BlockPos;
import de.booze.api.multiblock.IMultiblock;
import de.booze.api.multiblock.events.MultiblockPartDeleteEvent;
import de.booze.api.multiblock.events.MultiblockPartEvent;
import de.booze.api.multiblock.events.MultiblockPartPlacedEvent;
import de.booze.handlers.BoozeBlocks;
import de.booze.handlers.CraftingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityBarrel extends TileBase implements ISidedTexture, IMultiblock, IFluidHandler {
	
	public FluidTankAdv tank = new FluidTankAdv(0);
	
	private boolean placed = false;
	private boolean fresh = false;
	private boolean reloaded = false;
	private boolean isMultiblockHost = false;
	private BlockPos[] multiblockparts;
	private boolean isMultiblockPart = false;
	private int ticker = 0;

	public boolean isClosed = false;
	
	@Override
	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
		super.readFromNBT(paramNBTTagCompound);
		isMultiblockHost = paramNBTTagCompound.getBoolean("isMaster");
		isMultiblockPart = paramNBTTagCompound.getBoolean("isPart");
		isClosed = paramNBTTagCompound.getBoolean("isClosed");
		ticker = paramNBTTagCompound.getInteger("ticker");
		if(isMultiblockHost) {
			tank.setCapacity(paramNBTTagCompound.getInteger("tankCapacity"));
			tank.readFromNBT(paramNBTTagCompound);
			NBTTagCompound parts = paramNBTTagCompound.getCompoundTag("parts");
			int l = parts.getInteger("length");
			multiblockparts = new BlockPos[l];
			for(int i = 0; i < l; i++) {
				int x = parts.getInteger("part_" + i + "_x");
				int y = parts.getInteger("part_" + i + "_y");
				int z = parts.getInteger("part_" + i + "_z");
				multiblockparts[i] = new BlockPos(x, y, z);
			}
		}
		reloaded = true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
		super.writeToNBT(paramNBTTagCompound);
		paramNBTTagCompound.setBoolean("isMaster", isMultiblockHost);
		paramNBTTagCompound.setBoolean("isPart", isMultiblockPart);
		paramNBTTagCompound.setBoolean("isClosed", isClosed);
		paramNBTTagCompound.setInteger("ticker", ticker);
		if(isMultiblockHost) {
			paramNBTTagCompound.setInteger("tankCapacity", tank.getCapacity());
			tank.writeToNBT(paramNBTTagCompound);
			NBTTagCompound parts = new NBTTagCompound();
			parts.setInteger("length", multiblockparts.length);
			int i = 0;
			for(BlockPos bp : multiblockparts) {
				parts.setInteger("part_" + i + "_x", bp.x);
				parts.setInteger("part_" + i + "_y", bp.y);
				parts.setInteger("part_" + i + "_z", bp.z);
				i++;
			}
			paramNBTTagCompound.setTag("parts", parts);
		}
	}
	
	@Override
	public IIcon getTexture(int arg0, int arg1) {
		return BoozeMod.blocks.blockBarrel.getIcon(arg0, arg1);
	}
	
	@Override
	public void multiblockTick() {
		if(fresh && isMultiblockHost) {
			if(multiblockparts.length == 26)
				tank = new FluidTankAdv(100000);
			if(multiblockparts.length == 34)
				tank = new FluidTankAdv(125000);
			
			buildPartReference();
			fresh = false;
		}
		if(isMultiblockHost && reloaded) {
			buildPartsAfterReload();
			reloaded = false;
		}
		if(isClosed && this.tank.getFluid() != null && this.tank.getFluidAmount() > 0) {
			ticker++;
			Object[] recipe = CraftingHandler.checkBarrelFermentationRecipes(this.tank.getFluid().fluidID);
			if(recipe != null)
			if(ticker >= (int)recipe[2]) {
				this.tank.setFluid(new FluidStack((Fluid) recipe[1], tank.getFluid().amount));
				ticker = 0;
			}
		} else {
			ticker = 0;
		}
	}
	
	private void buildPartsAfterReload() {
		for(BlockPos partPos : multiblockparts) {
			if(worldObj.getTileEntity(partPos.x, partPos.y, partPos.z) != null && worldObj.getTileEntity(partPos.x, partPos.y, partPos.z) instanceof TileEntityBarrel) {
				TileEntityBarrel barrel = (TileEntityBarrel) worldObj.getTileEntity(partPos.x, partPos.y, partPos.z);
				barrel.placed = true;
				barrel.reloaded = false;
				barrel.multiblockparts = multiblockparts;
				barrel.tank = tank;
				barrel.fresh = false;
			}
		}
	}

	@Override
	public boolean openGui(EntityPlayer paramEntityPlayer) {
		if(isMultiblockPart) {
			PacketHandler.sendTo(getGuiPacket(), paramEntityPlayer);
			if(tank.getCapacity() >= 0)
				paramEntityPlayer.openGui(BoozeMod.INSTANCE, BoozeProps.GuiID.BARREL.ordinal(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		return isMultiblockPart;
	}
	
	@Override
	public void updateEntity() {
		if(!reloaded || isMultiblockHost) {
			if(!placed && !reloaded) {
				if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER)) {
					MinecraftForge.EVENT_BUS.post(new MultiblockPartPlacedEvent(worldObj, this));
					fresh = true;
				}
				placed = true;
			}
			if(isMultiblockHost)
				multiblockTick();
		}
		super.updateEntity();
	}
	
	public void buildPartReference() {
		for(BlockPos pos : multiblockparts) {
			TileEntity tile = worldObj.getTileEntity(pos.x, pos.y, pos.z);
			if(tile != null)
				if(tile instanceof TileEntityBarrel) {
					TileEntityBarrel barrel = (TileEntityBarrel) tile;
					barrel.tank = tank;
				}
		}
	}
	
	@Override
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
		packet.addInt(tank.getCapacity());
		packet.addFluidStack(tank.getFluid());
		return packet;
	}
	
	@Override
	protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {
		super.handleGuiPacket(paramPacketCoFHBase);
		tank.setCapacity(paramPacketCoFHBase.getInt());
		tank.setFluid(paramPacketCoFHBase.getFluidStack());
	}
	
	@Override
	public void onNeighborTileChange(int x, int y, int z) {
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if(tile != null && tile instanceof IMultiblock)
			MinecraftForge.EVENT_BUS.post(new MultiblockPartDeleteEvent(worldObj, this));
		super.onNeighborTileChange(x, y, z);
	}
	
	@Override
	public void validate() {
		super.validate();
		if(isMultiblockPart == false)
			placed = false;
	}
	
	@Override
	public void blockBroken() {
		super.blockBroken();
		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER))
			MinecraftForge.EVENT_BUS.post(new MultiblockPartDeleteEvent(worldObj, this));
	}

	@Override
	public BlockMeta getBlockMeta() {
		return new BlockMeta(worldObj.getBlock(xCoord, yCoord, zCoord), worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}

	@Override
	public BlockPos getPos() {
		return new BlockPos(xCoord, yCoord, zCoord);
	}

	@Override
	public void setMultiblock(BlockPos[] other) {
		multiblockparts = other;
	}

	@Override
	public BlockPos[] getMultiBlock() {
		return multiblockparts;
	}

	@Override
	public void activateMultiblock() {
		isMultiblockPart = true;
	}

	@Override
	public void disableMultiblock() {
		isMultiblockPart = false;
		isMultiblockHost = false;
	}

	@Override
	public boolean isMultiblockPart() {
		return isMultiblockPart;
	}

	@Override
	public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
		return isMultiblockPart && !isClosed;
	}

	@Override
	public boolean canFill(ForgeDirection arg0, Fluid arg1) {
		return isMultiblockPart && !isClosed;
	}

	@Override
	public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
		if(!isClosed)
			return tank.drain(arg1, arg2);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection arg0, int arg1, boolean arg2) {
		if(!isClosed)
			return tank.drain(arg1, arg2);
		return null;
	}

	@Override
	public int fill(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
		if(!isClosed)
			return tank.fill(arg1, arg2);
		return 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
		return new FluidTankInfo[]{ tank.getInfo() };
	}

	public IFluidTank getTank() {
		return tank;
	}

	@Override
	public void setHost() {
		isMultiblockHost = true;
	}
	
	public PacketCoFHBase getModePacket()
	{
	    PacketCoFHBase localPacketCoFHBase = super.getModePacket();

	    localPacketCoFHBase.addBool(this.isClosed);

	    return localPacketCoFHBase;
	}
	
	@Override
	protected void handleModePacket(PacketCoFHBase paramPacketCoFHBase) {
		super.handleModePacket(paramPacketCoFHBase);
		this.isClosed = paramPacketCoFHBase.getBool();
		for(BlockPos bp : multiblockparts)
			((TileEntityBarrel)worldObj.getTileEntity(bp.x, bp.y, bp.z)).isClosed = this.isClosed;
	}

	public void setMode(boolean paramBoolean)
	{
	    boolean bool = this.isClosed;
	    this.isClosed = paramBoolean;
	    sendModePacket();
	}

	private void sendModePacket() {
		PacketHandler.sendToServer(getModePacket());
	}
}
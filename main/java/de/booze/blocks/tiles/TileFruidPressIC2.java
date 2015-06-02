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
import net.minecraft.nbt.NBTTagList;
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

public class TileFruidPressIC2 extends TileFruidPress implements IEnergySink {
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!this.addedToEnergyNet) onLoaded();
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
				if ((TileFruidPressIC2.this.isInvalid()) || (!world.blockExists(TileFruidPressIC2.this.xCoord, TileFruidPressIC2.this.yCoord, TileFruidPressIC2.this.zCoord))) return;
				
				TileFruidPressIC2.this.onLoaded();
				
				if ((!TileFruidPressIC2.this.isInvalid()) && (TileFruidPressIC2.this.enableUpdateEntity()))
					world.loadedTileEntityList.add(TileFruidPressIC2.this);
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
}
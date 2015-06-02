package de.booze.blocks.tiles;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.IC2;
import ic2.core.ITickCallback;

import java.io.IOException;

import de.booze.BoozeProps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileFruidGrinderIC2 extends TileFruidGrinder implements IEnergySink {
	
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
				if ((TileFruidGrinderIC2.this.isInvalid()) || (!world.blockExists(TileFruidGrinderIC2.this.xCoord, TileFruidGrinderIC2.this.yCoord, TileFruidGrinderIC2.this.zCoord))) return;
				
				TileFruidGrinderIC2.this.onLoaded();
				
				if ((!TileFruidGrinderIC2.this.isInvalid()) && (TileFruidGrinderIC2.this.enableUpdateEntity()))
					world.loadedTileEntityList.add(TileFruidGrinderIC2.this);
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

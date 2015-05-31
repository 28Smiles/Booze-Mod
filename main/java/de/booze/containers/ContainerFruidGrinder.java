package de.booze.containers;

import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotEnergy;
import cofh.lib.gui.slot.SlotRemoveOnly;
import cofh.lib.gui.slot.SlotValidated;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import de.booze.blocks.tiles.TileFruidGrinder;

public class ContainerFruidGrinder extends ContainerBoozeBase implements ISlotValidator {
	
	TileFruidGrinder myTile;

	public ContainerFruidGrinder(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
	{
		super(paramInventoryPlayer, paramTileEntity);
		this.myTile = ((TileFruidGrinder)paramTileEntity);
		addSlotToContainer(new SlotValidated(this, this.myTile, TileFruidGrinder.Slots.INPUT.ordinal(), 67, 41));
		addSlotToContainer(new SlotRemoveOnly(this.myTile, TileFruidGrinder.Slots.OUTPUT.ordinal(), 96, 61));
		addSlotToContainer(new SlotEnergy(this.myTile, this.myTile.getChargeSlot(), 8, 40));
	}

	public boolean isItemValid(ItemStack paramItemStack)
	{
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer p_onContainerClosed_1_) {
		super.onContainerClosed(p_onContainerClosed_1_);
	}
}

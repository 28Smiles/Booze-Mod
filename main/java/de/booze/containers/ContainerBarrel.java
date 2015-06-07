package de.booze.containers;

import de.booze.blocks.tiles.TileEntityBarrel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class ContainerBarrel extends ContainerBoozeBase {

	private TileEntityBarrel myTile;

	public ContainerBarrel(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(paramInventoryPlayer, paramTileEntity);
		this.myTile = ((TileEntityBarrel)paramTileEntity);
	}
}

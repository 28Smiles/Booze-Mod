package de.booze.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import de.booze.BoozeProps;
import de.booze.containers.ContainerFruidGrinder;
import de.booze.gui.GuiFruidGrinder;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		switch(id) {
			case 1: return new GuiFruidGrinder(player.inventory, tileentity);
			default: return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		switch(id) {
			case 1: return new ContainerFruidGrinder(player.inventory, tileentity);
			default: return null;
		}
	}

}

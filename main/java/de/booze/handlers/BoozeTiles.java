package de.booze.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.blocks.tiles.TileFruidGrinder;
import de.booze.blocks.tiles.TileFruidPress;

public class BoozeTiles {
	
	public void init() {
		
	}
	
	public void register() {
		GameRegistry.registerTileEntity(TileFruidGrinder.class, "booze.fruidgrinder");
		GameRegistry.registerTileEntity(TileFruidPress.class, "booze.fruidpress");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		
	}
}

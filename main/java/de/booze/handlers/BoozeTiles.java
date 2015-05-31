package de.booze.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.blocks.tiles.TileFruidGrinder;

public class BoozeTiles {
	
	public void init() {
		
	}
	
	public void register() {
		GameRegistry.registerTileEntity(TileFruidGrinder.class, "booze.fruidgrinder");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		
	}
}

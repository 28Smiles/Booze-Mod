package de.booze.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.BoozeMod;
import de.booze.blocks.tiles.TileFruidGrinder;
import de.booze.blocks.tiles.TileFruidGrinderIC2;
import de.booze.blocks.tiles.TileFruidPress;
import de.booze.blocks.tiles.TileFruidPressIC2;

public class BoozeTiles {
	
	public void init() {
		
	}
	
	public void register() {
		if(BoozeMod.ic2Enabled)
			registerIC2();
		else
			registerNorm();
	}
	
	private void registerIC2() {
		GameRegistry.registerTileEntity(TileFruidGrinderIC2.class, "booze.fruidgrinder");
		GameRegistry.registerTileEntity(TileFruidPressIC2.class, "booze.fruidpress");
	}
	
	private void registerNorm() {
		GameRegistry.registerTileEntity(TileFruidGrinder.class, "booze.fruidgrinder");
		GameRegistry.registerTileEntity(TileFruidPress.class, "booze.fruidpress");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		
	}
}

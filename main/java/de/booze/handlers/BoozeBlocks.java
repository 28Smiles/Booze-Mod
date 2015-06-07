package de.booze.handlers;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.blocks.BlockBarrel;
import de.booze.blocks.BlockFruidGrinder;
import de.booze.blocks.BlockFruidPress;

public class BoozeBlocks {
	
	public BlockFruidGrinder blockFruidGrinder;
	public BlockFruidPress blockFruidPress;
	public BlockBarrel blockBarrel;
	
	public void init() {
		blockFruidGrinder = new BlockFruidGrinder();
		blockFruidPress = new BlockFruidPress();
		blockBarrel = new BlockBarrel();
		
		blockFruidGrinder.preInit();
		blockFruidPress.preInit();
		blockBarrel.preInit();
		register();
	}
	
	public void register() {
		
		blockFruidGrinder.initialize();
		blockFruidPress.initialize();
		blockBarrel.initialize();
		
		blockFruidGrinder.postInit();
		blockFruidPress.postInit();
		blockBarrel.postInit();
	}
}

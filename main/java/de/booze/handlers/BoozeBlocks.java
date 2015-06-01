package de.booze.handlers;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.blocks.BlockFluidMash;
import de.booze.blocks.BlockFruidGrinder;
import de.booze.blocks.BlockFruidPress;

public class BoozeBlocks {
	
	public BlockFluidMash blockFluidMash;
	public BlockFruidGrinder blockFruidGrinder;
	public BlockFruidPress blockFruidPress;
	
	public void init() {
		blockFluidMash = new BlockFluidMash();
		blockFruidGrinder = new BlockFruidGrinder();
		blockFruidPress = new BlockFruidPress();
		
		blockFruidGrinder.preInit();
		blockFruidPress.preInit();
		register();
	}
	
	public void register() {
		GameRegistry.registerBlock(blockFluidMash, "FluidMash");
		
		blockFruidGrinder.initialize();
		blockFruidPress.initialize();
		
		blockFruidGrinder.postInit();
		blockFruidPress.postInit();
	}
}

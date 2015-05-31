package de.booze.handlers;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.blocks.BlockFluidMash;
import de.booze.blocks.BlockFruidGrinder;

public class BoozeBlocks {
	
	public BlockFluidMash blockFluidMash;
	public BlockFruidGrinder blockFruidGrinder;
	
	public void init() {
		blockFluidMash = new BlockFluidMash();
		blockFruidGrinder = new BlockFruidGrinder();
		
		blockFruidGrinder.preInit();
		register();
	}
	
	public void register() {
		GameRegistry.registerBlock(blockFluidMash, "FluidMash");
		
		blockFruidGrinder.initialize();
		
		
		blockFruidGrinder.postInit();
	}
}

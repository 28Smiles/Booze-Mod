package de.booze.handlers;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.blocks.BlockFluidMash;

public class BoozeBlocks {
	
	public BlockFluidMash blockFluidMash;
	
	public void init() {
		blockFluidMash = new BlockFluidMash();
		register();
	}
	
	public void register() {
		GameRegistry.registerBlock(blockFluidMash, "FluidMash");
	}
}

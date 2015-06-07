package de.booze.handlers;

import net.minecraft.init.Blocks;
import de.booze.BoozeMod;
import de.booze.api.BoozeAPI;
import de.booze.api.data.BlockMeta;

public class BoozeMultiblocks {

	public void init() {
		BlockMeta blockBarrel = new BlockMeta(BoozeMod.blocks.blockBarrel, 0);
		BlockMeta[][][] barrelsmall = {
				{
					{blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel}
				},
				{
					{blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, null, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel}
				},
				{
					{blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel}
				}
		};
		BlockMeta[][][] barrelmedium = {
				{
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel}
				},
				{
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, null, null, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel}
				},
				{
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel},
					{blockBarrel, blockBarrel, blockBarrel, blockBarrel}
				}
		};
		
		BoozeAPI.registry.registerNewMultiblockStructure(barrelsmall);
		BoozeAPI.registry.registerNewMultiblockStructure(barrelmedium);
	}
	
}

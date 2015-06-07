package de.booze.api.multiblock;

import java.util.ArrayList;
import java.util.List;

import de.booze.api.data.BlockMeta;

public class MultiBlockRegistry implements IMultiBlockRegistry {
	
	private static List<MultiBlockStructure> multiblocks = new ArrayList<MultiBlockStructure>();
	
	public void registerNewMultiblockStructure(BlockMeta[][][] structure) {
		multiblocks.add(new MultiBlockStructure(structure));
	}
	
	public static BlockMeta[][][] checkStructures(BlockMeta[][][] mbsructure) {
		for(MultiBlockStructure mbs : multiblocks) {
			int i = mbs.contains(mbsructure);
			if(i != -1)
				return mbs.getStructure(i);
		}
		return null;
	}
}

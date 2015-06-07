package de.booze.api.multiblock;

import de.booze.api.data.BlockMeta;

public interface IMultiBlockRegistry {

	public abstract void registerNewMultiblockStructure(BlockMeta[][][] structure);
}

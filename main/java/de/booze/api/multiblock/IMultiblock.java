package de.booze.api.multiblock;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import de.booze.api.data.BlockMeta;
import de.booze.api.data.BlockPos;

public interface IMultiblock {
	
	public abstract BlockMeta getBlockMeta();
	
	public abstract BlockPos getPos();
	
	public abstract void setMultiblock(BlockPos[] other);
	
	public abstract BlockPos[] getMultiBlock();
	
	public default List<BlockPos> searchForMultiblockParts(World world, List<BlockPos> list) {
		if(!list.contains(getPos())) {
			list.add(getPos());
			for(BlockPos pos : getPos().getNeighbors()) {
				TileEntity tile = world.getTileEntity(pos.x, pos.y, pos.z);
				if(tile instanceof IMultiblock)
					list = ((IMultiblock)tile).searchForMultiblockParts(world, list);
			}
		}
		return list;
	}
	
	public abstract boolean isMultiblockPart();
	
	public abstract void activateMultiblock();
	
	public abstract void disableMultiblock();
	
	public abstract void multiblockTick();

	public abstract void setHost();
}

package de.booze.api.data;

import net.minecraft.block.Block;

public class BlockMeta implements Comparable {
	
	private Block block;
	private int meta;
	
	public BlockMeta(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
	
	@Override
	public BlockMeta clone() {
		return new BlockMeta(block, meta);
	}
	
	@Override
	public int hashCode() {
		return block.hashCode() * meta;
	}
	
	@Override
	public String toString() {
		return block.getUnlocalizedName() + ":" + meta;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BlockMeta) {
			BlockMeta bm = (BlockMeta) obj;
			if(bm.meta == this.meta)
				if(bm.block.equals(this.block))
					return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object obj) {
		if(obj instanceof BlockMeta) {
			BlockMeta bm = (BlockMeta) obj;
			if(bm.meta < this.meta)
				return 1;
			if(bm.meta > this.meta)
				return -1;
		}
		return 0;
	}
}

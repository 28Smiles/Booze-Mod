package de.booze.api.data;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPos implements Comparable {
	public int x, y, z;

	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getChunkX() { return x >> 4; }
	public int getChunkZ() { return z >> 4; }
	public long getChunkXZHash() { return ChunkCoordIntPair.chunkXZ2Int(x >> 4, z >> 4); }
	
	@Override
	public boolean equals(Object other) {
		if(other == null)
		{ return false; }
		else if(other instanceof BlockPos) {
			BlockPos otherTriplet = (BlockPos)other;
			return this.x == otherTriplet.x && this.y == otherTriplet.y && this.z == otherTriplet.z;
		}
		else {
			return false;
		}
	}
	
	public void translate(ForgeDirection dir) {
		this.x += dir.offsetX;
		this.y += dir.offsetY;
		this.z += dir.offsetZ;
	}
	
	public boolean equals(int x, int y, int z) {
		return this.x == x && this.y == y && this.z == z;
	}
	
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + this.x;
		hash = 71 * hash + this.y;
		hash = 71 * hash + this.z;
		return hash;
	}

	public BlockPos copy() {
		return new BlockPos(x, y, z);
	}
	
	public void copy(BlockPos other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public BlockPos[] getNeighbors() {
		return new BlockPos[]{
			new BlockPos(x + 1, y, z),
			new BlockPos(x - 1, y, z),
			new BlockPos(x, y + 1, z),
			new BlockPos(x, y - 1, z),
			new BlockPos(x, y, z + 1),
			new BlockPos(x, y, z - 1)
		};
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof BlockPos) {
			BlockPos other = (BlockPos)o;
			if(this.x < other.x) { return -1; }
			else if(this.x > other.x) { return 1; }
			else if(this.y < other.y) { return -1; }
			else if(this.y > other.y) { return 1; }
			else if(this.z < other.z) { return -1; }
			else if(this.z > other.z) { return 1; }
			else { return 0; }
		}
		return 0;
	}
	
	public ForgeDirection getDirectionFromSourceCoords(int x, int y, int z) {
		if(this.x < x) { return ForgeDirection.WEST; }
		else if(this.x > x) { return ForgeDirection.EAST; }
		else if(this.y < y) { return ForgeDirection.DOWN; }
		else if(this.y > y) { return ForgeDirection.UP; }
		else if(this.z < z) { return ForgeDirection.SOUTH; }
		else if(this.z > z) { return ForgeDirection.NORTH; }
		else { return ForgeDirection.UNKNOWN; }
	}
	
	public ForgeDirection getOppositeDirectionFromSourceCoords(int x, int y, int z) {
		if(this.x < x) { return ForgeDirection.EAST; }
		else if(this.x > x) { return ForgeDirection.WEST; }
		else if(this.y < y) { return ForgeDirection.UP; }
		else if(this.y > y) { return ForgeDirection.DOWN; }
		else if(this.z < z) { return ForgeDirection.NORTH; }
		else if(this.z > z) { return ForgeDirection.SOUTH; }
		else { return ForgeDirection.UNKNOWN; }
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d, %d)", this.x, this.y, this.z);
	}

	public int compareTo(int xCoord, int yCoord, int zCoord) {
		if(this.x < xCoord) { return -1; }
		else if(this.x > xCoord) { return 1; }
		else if(this.y < yCoord) { return -1; }
		else if(this.y > yCoord) { return 1; }
		else if(this.z < zCoord) { return -1; }
		else if(this.z > zCoord) { return 1; }
		else { return 0; }
	}
}

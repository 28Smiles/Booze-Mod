package de.booze.api.multiblock;

import java.util.ArrayList;
import java.util.List;

import de.booze.api.data.BlockMeta;
import de.booze.api.data.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * 0,0,0 is the Top, South, West Block (Facing North)
 * @author Leon aka. 28Smiles
 */
public class MultiBlockStructure {
	
	public BlockMeta[][][][] multiblock;
	
	public MultiBlockStructure(BlockMeta[][][] multiblock) {
		this.multiblock = new BlockMeta[4][][][];
		this.multiblock[0] = multiblock.clone();
		this.multiblock[1] = rotatePlainRight(multiblock).clone();
		this.multiblock[2] = rotatePlainRight(this.multiblock[1]).clone();
		this.multiblock[3] = rotatePlainRight(this.multiblock[2]).clone();
	}
	
	public boolean isMultiBlock(IMultiblock[][][] multiblocks) {
		BlockMeta[][][] multiblock = translate(multiblocks);
		return contains(multiblock) != -1;
	}
	
	public int contains(BlockMeta[][][] multiblock) {
		for(int i = 0; i < this.multiblock.length; i++)
			if(equals(this.multiblock[i], multiblock))
				return i;
		return -1;
	}
	
	public boolean equals(BlockMeta[][][] multiblockA, BlockMeta[][][] multiblockB) {
		if(multiblockA.length == multiblockB.length) {
			for(int j = 0; j < multiblockA.length; j++)
				if(multiblockA[j].length == multiblockB[j].length) {
					for(int k = 0; k < multiblockA[j].length; k++)
						if(multiblockA[j][k].length == multiblockB[j][k].length) {
							for(int l = 0; l < multiblockA[j][k].length; l++)
								if(multiblockA[j][k][l] != null) {
									if(multiblockB[j][k][l] != null) {
										if(!multiblockA[j][k][l].equals(multiblockB[j][k][l]))
											return false;
									} else
										return false;
								} else
									if(multiblockB[j][k][l] != null)
										return false;
						} else return false;
				} else return false;
		} else return false;
		return true;
	}
	
	public BlockMeta[][][] getStructure(int n) {
		return multiblock[n];
	}
	
	public BlockMeta[][][] rotatePlainRight(BlockMeta[][][] multiblocks) {
		int zMax = 0;
		for(int i = 0; i < multiblocks.length; i++)
			for(int j = 0; j < multiblocks[i].length; j++)
				if(multiblocks[i][j].length > zMax)
					zMax = multiblocks[i][j].length;
		BlockMeta[][][] bma = new BlockMeta[zMax][][];
		for(int i = 0; i < zMax; i++) {
			bma[i] = new BlockMeta[multiblocks[0].length][];
			for(int j = 0; j < multiblocks[0].length; j++)
				bma[i][j] = new BlockMeta[multiblocks.length];
		}
		
		for(int i = 0; i < multiblocks.length; i++)
			for(int j = 0; j < multiblocks[i].length; j++) {
				int l = multiblocks[i][j].length;
				for(int k = 0; k < multiblocks[i][j].length; k++) {
					l--;
					if(multiblocks[i][j][l] != null)
						bma[k][j][i] = multiblocks[i][j][l].clone();
					else
						bma[k][j][i] = null;
				}
			}
		return bma;
	}
	
	public static BlockMeta[][][] translate(IMultiblock[][][] multiblocks) {
		BlockMeta[][][] bma = new BlockMeta[multiblocks.length][][];
		for(int i = 0; i < multiblocks.length; i++) {
			bma[i] = new BlockMeta[multiblocks[i].length][];
			for(int j = 0; j < multiblocks[i].length; j++) {
				bma[i][j] = new BlockMeta[multiblocks[i][j].length];
				for(int k = 0; k < multiblocks[i][j].length; k++)
					if(multiblocks[i][j][k] != null)
						bma[i][j][k] = multiblocks[i][j][k].getBlockMeta().clone();
					else
						bma[i][j][k] = null;
			}
		}
		return bma;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MultiBlockStructure) {
			MultiBlockStructure mbs = (MultiBlockStructure) obj;
			if(mbs.multiblock.length == this.multiblock.length) {
				for(int i = 0; i < mbs.multiblock.length; i++)
					if(mbs.multiblock[i].length == this.multiblock[i].length) {
						for(int j = 0; j < mbs.multiblock[i].length; j++)
							if(mbs.multiblock[i][j].length == this.multiblock[i][j].length) {
								for(int k = 0; k < mbs.multiblock[i][j].length; k++)
									if(mbs.multiblock[i][j][k].length == this.multiblock[i][j][k].length) {
										for(int l = 0; l < mbs.multiblock[i][j][k].length; l++)
											if(mbs.multiblock[i][j][k][l] != null) {
												if(this.multiblock[i][j][k][l] != null)
													if(!mbs.multiblock[i][j][k][l].equals(this.multiblock[i][j][k]))
													return false;
											} else
												if(this.multiblock[i][j][k][l] != null)
													return false;
									} else return false;
							} else return false;
					} else return false;
			} else return false;
			
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return multiblock.hashCode();
	}
	
	@Override
	public MultiBlockStructure clone() {
		return new MultiBlockStructure(multiblock[0].clone());
	}
}

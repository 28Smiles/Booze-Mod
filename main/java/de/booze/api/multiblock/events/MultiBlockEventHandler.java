package de.booze.api.multiblock.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import de.booze.api.BoozeAPI;
import de.booze.api.data.BlockMeta;
import de.booze.api.data.BlockPos;
import de.booze.api.multiblock.IMultiblock;
import de.booze.api.multiblock.MultiBlockRegistry;

public class MultiBlockEventHandler {

	@SubscribeEvent
	public void multiblockPartPlaced(MultiblockPartPlacedEvent event) {
		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER)) {
			List<BlockPos> posList = event.multiBlockTile.searchForMultiblockParts(event.world, new ArrayList<BlockPos>());
			if(event.multiBlockTile.getMultiBlock() != null)
				if(event.multiBlockTile.getMultiBlock().hashCode() == posList.toArray(new BlockPos[posList.size()]).hashCode())
					return;
			BlockPos minPos;
			BlockPos maxPos;
			minPos = posList.get(0).copy();
			maxPos = posList.get(0).copy();
			
			for(BlockPos pos : posList) {
				if(pos.x > maxPos.x)
					maxPos.x = pos.x;
				if(pos.y > maxPos.y)
					maxPos.y = pos.y;
				if(pos.z > maxPos.z)
					maxPos.z = pos.z;
				if(pos.x < minPos.x)
					minPos.x = pos.x;
				if(pos.y < minPos.y)
					minPos.y = pos.y;
				if(pos.z < minPos.z)
					minPos.z = pos.z;
			}
			BlockMeta[][][] potentiualMultiblock = new BlockMeta[maxPos.x - minPos.x + 1][maxPos.y - minPos.y + 1][maxPos.z - minPos.z + 1];
			for(BlockMeta[][] subArray : potentiualMultiblock) {
				subArray = new BlockMeta[maxPos.y - minPos.y + 1][maxPos.z - minPos.z + 1];
				for(BlockMeta[] subsubArray : subArray)
					subsubArray = new BlockMeta[maxPos.z - minPos.z + 1];
			}
			
			for(BlockPos pos : posList)
				potentiualMultiblock[pos.x - minPos.x][pos.y - minPos.y][pos.z - minPos.z] = new BlockMeta(event.world.getBlock(pos.x, pos.y, pos.z), event.world.getBlockMetadata(pos.x, pos.y, pos.z));
			
			BlockMeta[][][] multiblock = MultiBlockRegistry.checkStructures(potentiualMultiblock);
			if(multiblock != null) {
				for(BlockPos pos : posList) {
					((IMultiblock)event.world.getTileEntity(pos.x, pos.y, pos.z)).disableMultiblock();
					((IMultiblock)event.world.getTileEntity(pos.x, pos.y, pos.z)).setMultiblock(posList.toArray(new BlockPos[posList.size()]));
					((IMultiblock)event.world.getTileEntity(pos.x, pos.y, pos.z)).activateMultiblock();
				}
				((IMultiblock)event.world.getTileEntity(posList.get(0).x, posList.get(0).y, posList.get(0).z)).setHost();
			}
		} else {
			BoozeAPI.log.warn("MultiblockPartPlacedEvent on wrong side detected!");
			return;
		}
	}
	
	@SubscribeEvent
	public void multiblockPartDelete(MultiblockPartDeleteEvent event) {
		if(event.multiBlockTile.isMultiblockPart())
			for(BlockPos pos : event.multiBlockTile.getMultiBlock()) {
				TileEntity tile = event.world.getTileEntity(pos.x, pos.y, pos.z);
					if(tile != null && tile instanceof IMultiblock)
							((IMultiblock)tile).disableMultiblock();
			}
		for(BlockPos pos : event.multiBlockTile.getPos().getNeighbors()) {
			TileEntity tile = event.world.getTileEntity(pos.x, pos.y, pos.z);
			if(tile instanceof IMultiblock) {
				((IMultiblock)tile).disableMultiblock();
				tile.validate();
			}
		}
	}
}

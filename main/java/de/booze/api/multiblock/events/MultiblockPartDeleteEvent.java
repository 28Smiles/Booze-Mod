package de.booze.api.multiblock.events;

import net.minecraft.world.World;
import de.booze.api.multiblock.IMultiblock;

public class MultiblockPartDeleteEvent extends MultiblockPartEvent {

	public MultiblockPartDeleteEvent(World world, IMultiblock multiBlockTile) {
		super(world, multiBlockTile);
	}

}

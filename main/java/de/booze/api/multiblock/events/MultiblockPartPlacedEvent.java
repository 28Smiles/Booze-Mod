package de.booze.api.multiblock.events;

import net.minecraft.world.World;
import de.booze.api.multiblock.IMultiblock;

public class MultiblockPartPlacedEvent extends MultiblockPartEvent {

	public MultiblockPartPlacedEvent(World world, IMultiblock multiBlockTile) {
		super(world, multiBlockTile);
	}

}

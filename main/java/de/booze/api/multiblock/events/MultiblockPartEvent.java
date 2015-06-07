package de.booze.api.multiblock.events;

import de.booze.api.multiblock.IMultiblock;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class MultiblockPartEvent extends WorldEvent {
	
	public final IMultiblock multiBlockTile;
	
	public MultiblockPartEvent(World world, IMultiblock multiBlockTile) {
		super(world);
		this.multiBlockTile = multiBlockTile;
	}

}

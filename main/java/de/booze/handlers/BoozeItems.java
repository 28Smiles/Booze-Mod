package de.booze.handlers;

import cofh.core.item.ItemBucket;
import cofh.core.util.fluid.BucketHandler;
import de.booze.BoozeMod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BoozeItems {
	
	public ItemBucket itemBucket;
	
	public ItemStack bucketMash;
	
	public void init() {
		itemBucket = (ItemBucket)new ItemBucket("booze").setUnlocalizedName("bucket").setCreativeTab(BoozeMod.tabCommon);
		
		bucketMash = itemBucket.addOreDictItem(0, "bucketMash", 0);
		
		register();
	}
	
	public void register() {
		BucketHandler.registerBucket(BoozeMod.blocks.blockFluidMash, 0, bucketMash);
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(BoozeMod.fluids.fluidMash, 1000), bucketMash, FluidContainerRegistry.EMPTY_BUCKET);
	}
}

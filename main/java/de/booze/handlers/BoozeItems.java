package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import cofh.core.item.ItemBase;
import cofh.core.item.ItemBucket;
import cofh.core.util.fluid.BucketHandler;
import de.booze.BoozeMod;
import de.booze.items.BoozeItemBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BoozeItems {
	
	public Map<String, ItemStack> xml_buckets = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemFruid = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemDampRestidue = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemEmptyRestidue = new HashMap<String, ItemStack>();
	
	public BoozeItemBase itemFruid;
	public BoozeItemBase itemDampRestidue;
	public BoozeItemBase itemEmptyRestidue;
	
	public ItemBucket itemBucket;
	
	public ItemStack bucketMash;
	
	public void init() {
		itemBucket = (ItemBucket)new ItemBucket("booze").setUnlocalizedName("bucket").setCreativeTab(BoozeMod.tabCommon);
		itemDampRestidue = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("restidue.damp").setCreativeTab(BoozeMod.tabCommon);
		itemEmptyRestidue = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("restidue.empty").setCreativeTab(BoozeMod.tabCommon);
		itemFruid = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("fruid").setCreativeTab(BoozeMod.tabCommon);
		
		bucketMash = itemBucket.addOreDictItem(0, "bucketMash", 0);
		
		register();
	}
	
	public void register() {
		BucketHandler.registerBucket(BoozeMod.blocks.blockFluidMash, 0, bucketMash);
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(BoozeMod.fluids.fluidMash, 1000), bucketMash, FluidContainerRegistry.EMPTY_BUCKET);
	}
}

package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import cofh.core.item.ItemBase;
import cofh.core.item.ItemBucket;
import cofh.core.util.fluid.BucketHandler;
import de.booze.BoozeMod;
import de.booze.items.BoozeItemBase;
import de.booze.items.ItemMug;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BoozeItems {
	
	public Map<String, ItemStack> xml_buckets = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemFruid = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemDampResidue = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemEmptyResidue = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemBottle = new HashMap<String, ItemStack>();
	public Map<String, ItemStack> xml_itemMug = new HashMap<String, ItemStack>();
	
	public BoozeItemBase itemFruid;
	public BoozeItemBase itemDampResidue;
	public BoozeItemBase itemEmptyResidue;
	public BoozeItemBase itemBottle;
	public ItemMug itemMug;
	
	public ItemBucket itemBucket;
	
	public void init() {
		itemBucket = (ItemBucket)new ItemBucket("booze").setUnlocalizedName("bucket").setCreativeTab(BoozeMod.tabCommon);
		itemDampResidue = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("residue.damp").setCreativeTab(BoozeMod.tabCommon);
		itemEmptyResidue = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("residue.empty").setCreativeTab(BoozeMod.tabCommon);
		itemFruid = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("fruid").setCreativeTab(BoozeMod.tabCommon);
		itemBottle = (BoozeItemBase) new BoozeItemBase().setUnlocalizedName("bottle").setCreativeTab(BoozeMod.tabCommon);
		itemMug = (ItemMug) new ItemMug().setUnlocalizedName("mug").setCreativeTab(BoozeMod.tabCommon);
		
		register();
	}
	
	public void register() {
		
	}
}

package de.booze.xml;

import java.util.List;

import cofh.core.item.ItemBucket;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.handlers.BoozeFluids;
import de.booze.handlers.CraftingHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class XMLItemCreator {
	
	/**
	 * TODO Textures
	 * @param list
	 */
	public static void createMashes(List<Mash> list) {
		int meta_counter = 40;
		for(Mash mash : list) {
			Fluid fluid = new Fluid(mash.getId()).setDensity(mash.getDensity()).setViscosity(mash.getViscosity()).setTemperature(295).setLuminosity(0);
			BoozeFluids.registerFluid(fluid, mash.getId());
			BoozeMod.fluids.xml_fluids.put(mash.getId(), fluid);
			
			ItemStack bucketMash = BoozeMod.items.itemBucket.addOreDictItem(meta_counter, mash.getId());
			FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, 1000), bucketMash, FluidContainerRegistry.EMPTY_BUCKET);
			BoozeMod.items.xml_buckets.put(mash.getId(), bucketMash);
			
			ItemStack itemFruid;
			if(mash.getFruid_type().equals(ItemType.NEW)) {
				itemFruid = BoozeMod.items.itemFruid.addOreDictItem(meta_counter, mash.getId());
				BoozeMod.items.xml_itemFruid.put(mash.getId(), itemFruid);
			} else {
				itemFruid = new ItemStack(GameRegistry.findItem(mash.getFruid_id().split(":")[0], mash.getFruid_id().split(":")[1]), 1);
			}
			
			ItemStack itemRestidueDamp;
			if(mash.getResidue_damp_type().equals(ItemType.NEW)) {
				itemRestidueDamp = BoozeMod.items.itemDampRestidue.addOreDictItem(meta_counter, mash.getId());
				BoozeMod.items.xml_itemDampRestidue.put(mash.getId(), itemRestidueDamp);
			} else {
				itemRestidueDamp = new ItemStack(GameRegistry.findItem(mash.getResidue_damp_id().split(":")[0], mash.getResidue_damp_id().split(":")[1]), 1);
			}
			
			ItemStack itemRestidueEmpty;
			if(mash.getResidue_type().equals(ItemType.NEW)) {
				itemRestidueEmpty = BoozeMod.items.itemEmptyRestidue.addOreDictItem(meta_counter, mash.getId());
				BoozeMod.items.xml_itemEmptyRestidue.put(mash.getId(), itemRestidueEmpty);
			} else {
				itemRestidueEmpty = new ItemStack(GameRegistry.findItem(mash.getResidue_id().split(":")[0], mash.getResidue_id().split(":")[1]), 1);
			}
			
			CraftingHandler.addNewFruidGrindRecipe(itemFruid.copy(), itemRestidueDamp.copy(), new FluidStack(fluid, mash.getMash()), 160);
			CraftingHandler.addNewFruidPressRecipe(itemRestidueDamp.copy(), itemRestidueEmpty.copy(), new FluidStack(fluid, mash.getResidue_damp_mash()), 240);
			
			meta_counter++;
		}
	}
}

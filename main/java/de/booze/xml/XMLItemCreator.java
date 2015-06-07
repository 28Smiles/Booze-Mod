package de.booze.xml;

import java.util.List;

import cofh.core.item.ItemBucket;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.api.BoozeAPI;
import de.booze.api.data.ItemType;
import de.booze.handlers.BoozeFluids;
import de.booze.handlers.CraftingHandler;
import de.booze.xml.alcohol.Alcohol;
import de.booze.xml.alcohol.Booze;
import de.booze.xml.alcohol.Effect;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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
			Fluid fluid = new Fluid("mash." + mash.getId()).setDensity(mash.getDensity()).setViscosity(mash.getViscosity()).setTemperature(295).setLuminosity(0);
			BoozeFluids.registerFluid(fluid, "mash." + mash.getId());
			BoozeMod.fluids.xml_mash.put("mash." + mash.getId(), fluid);
			
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
	
	public static void createAlcohol(List<Alcohol> list) {
		ItemStack mug = BoozeMod.items.itemMug.addOreDictItem(0, "empty");
		BoozeMod.items.xml_itemMug.put("empty", mug);
		
		ItemStack bottle = BoozeMod.items.itemBottle.addOreDictItem(0, "empty");
		BoozeMod.items.xml_itemBottle.put("empty", bottle);
		
		int metacounter = 40;
		for(Alcohol alcohol : list) {
			final String id = alcohol.id;
			final String alcID = "alcohol." + alcohol.id;
			
			for(Booze booze : alcohol.booze) {
				final String boozeID = alcID + "." + booze.id;
				
				Fluid fluid = new Fluid(alcID + "." + booze.id).setDensity(1000).setViscosity(1000).setTemperature(295).setLuminosity(0);
				BoozeFluids.registerFluid(fluid, alcID + "." + booze.id);
				BoozeMod.fluids.xml_alcohol.put(alcID + "." + booze.id, fluid);
				
				if(BoozeMod.fluids.xml_alcohol.get(booze.educt) != null)
				    CraftingHandler.addNewBarrelFermentationRecipe(BoozeMod.fluids.xml_alcohol.get(booze.educt).getID(), fluid, Integer.parseInt(booze.maturityTime));
				else
					CraftingHandler.addNewBarrelFermentationRecipe(BoozeMod.fluids.xml_mash.get(booze.educt).getID(), fluid, Integer.parseInt(booze.maturityTime));
				
				if(booze.drinkable.equals("true")) {
					bottle = BoozeMod.items.itemBottle.addOreDictItem(metacounter, boozeID);
					FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, 750), bottle, BoozeMod.items.xml_itemBottle.get("empty"));
					BoozeMod.items.xml_itemBottle.put(boozeID, bottle);

					mug = BoozeMod.items.itemMug.addOreDictItem(metacounter, boozeID);
					
					if(booze.effect != null)
					for(Effect effect : booze.effect)
						BoozeMod.items.itemMug.addPotionEffectForItem(metacounter, new PotionEffect(Integer.parseInt(effect.id), Integer.parseInt(effect.duration)));
					
					FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, 250), mug, BoozeMod.items.xml_itemMug.get("empty"));
					BoozeMod.items.xml_itemMug.put(boozeID, bottle);
					
					metacounter++;
				}
				
				//TODO Distille
			}
		}
	}
}

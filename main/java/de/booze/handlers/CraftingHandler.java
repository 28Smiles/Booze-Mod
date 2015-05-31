package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CraftingHandler {
	
	private static Map<Item, Object[]> fruid_grinder_recipes = new HashMap<Item, Object[]>();
	
	public static void addNewFruidGrindRecipes(ItemStack input, ItemStack output_item, FluidStack output_fluid, int ticks) {
		fruid_grinder_recipes.put(input.getItem(), new Object[] { input, output_item, output_fluid, ticks });
	}
	
	/**
	 * Returns Recipe
	 * Object[] { inputstack, outputstack, outputfluidstack };
	 * @param input
	 * @return
	 */
	public static Object[] checkFruidGrindRecipes(ItemStack input) {
		Object[] recipe = fruid_grinder_recipes.get(input.getItem());
		if(recipe == null)
			return null;
		if(input.getItemDamage() == ((ItemStack)recipe[0]).getItemDamage())
		if(input.stackSize >= ((ItemStack)recipe[0]).stackSize) {
			return recipe;
		}
		return null;
	}
}

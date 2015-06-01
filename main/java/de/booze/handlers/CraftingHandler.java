package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import de.booze.crafting.ItemDamage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CraftingHandler {
	
	private static Map<ItemDamage, Object[]> fruid_grinder_recipes = new HashMap<ItemDamage, Object[]>();
	private static Map<ItemDamage, Object[]> fruid_press_recipes = new HashMap<ItemDamage, Object[]>();
	
	public static void addNewFruidGrindRecipe(ItemStack input, ItemStack output_item, FluidStack output_fluid, int ticks) {
		fruid_grinder_recipes.put(new ItemDamage(input.getItem(), input.getItemDamage()), new Object[] { input, output_item, output_fluid, ticks });
	}
	
	public static void addNewFruidPressRecipe(ItemStack input, ItemStack output_item, FluidStack output_fluid, int ticks) {
		fruid_press_recipes.put(new ItemDamage(input.getItem(), input.getItemDamage()), new Object[] { input, output_item, output_fluid, ticks });
	}
	
	/**
	 * Returns Recipe
	 * Object[] { inputstack, outputstack, outputfluidstack };
	 * @param input
	 * @return
	 */
	public static Object[] checkFruidGrindRecipes(ItemStack input) {
		Object[] recipe = fruid_grinder_recipes.get(new ItemDamage(input.getItem(), input.getItemDamage()));
		if(recipe == null)
			return null;
		if(input.getItemDamage() == ((ItemStack)recipe[0]).getItemDamage())
		if(input.stackSize >= ((ItemStack)recipe[0]).stackSize) {
			return recipe;
		}
		return null;
	}

	public static Object[] checkFruidPressRecipes(ItemStack input) {
		Object[] recipe = fruid_press_recipes.get(new ItemDamage(input.getItem(), input.getItemDamage()));
		if(recipe == null)
			return null;
		if(input.getItemDamage() == ((ItemStack)recipe[0]).getItemDamage())
		if(input.stackSize >= ((ItemStack)recipe[0]).stackSize) {
			return recipe;
		}
		return null;
	}
}

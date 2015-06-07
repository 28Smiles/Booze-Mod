package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import de.booze.BoozeMod;
import de.booze.api.data.ItemDamage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CraftingHandler {
	
	private static Map<ItemDamage, Object[]> fruid_grinder_recipes = new HashMap<ItemDamage, Object[]>();
	private static Map<ItemDamage, Object[]> fruid_press_recipes = new HashMap<ItemDamage, Object[]>();
	private static Map<Integer, Object[]> barrel_recipes = new HashMap<Integer, Object[]>();
	
	public static void addNewFruidGrindRecipe(ItemStack input, ItemStack output_item, FluidStack output_fluid, int ticks) {
		fruid_grinder_recipes.put(new ItemDamage(input.getItem(), input.getItemDamage()), new Object[] { input, output_item, output_fluid, ticks });
	}
	
	public static void addNewFruidPressRecipe(ItemStack input, ItemStack output_item, FluidStack output_fluid, int ticks) {
		fruid_press_recipes.put(new ItemDamage(input.getItem(), input.getItemDamage()), new Object[] { input, output_item, output_fluid, ticks });
	}
	
	public static void addNewBarrelFermentationRecipe(int fluidID, Fluid output, int time) {
		barrel_recipes.put(fluidID, new Object[] { fluidID, output, time });
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
	
	public static Object[] checkBarrelFermentationRecipes(Integer fluidID) {
		Object[] recipe = barrel_recipes.get(fluidID);
		return recipe;
	}
	
	public static void initRecipes() {
		CraftingManager.getInstance().addRecipe(new ItemStack(BoozeMod.blocks.blockBarrel, 1), new Object[] {
			"WWW", "WWW", "WWW", 'W', new ItemStack(Blocks.planks)
		});
		CraftingManager.getInstance().addRecipe(new ItemStack(BoozeMod.blocks.blockFruidGrinder, 1), new Object[] {
			"III", "S S", "SHS", 'I', new ItemStack(Items.iron_ingot), 'S', new ItemStack(Items.shears), 'H', new ItemStack(Blocks.hopper)
		});
		CraftingManager.getInstance().addRecipe(new ItemStack(BoozeMod.blocks.blockFruidPress, 1), new Object[] {
			"III", "S S", "SHS", 'I', new ItemStack(Items.iron_ingot), 'S', new ItemStack(Items.shears), 'H', new ItemStack(Blocks.hopper)
		});
	}
}

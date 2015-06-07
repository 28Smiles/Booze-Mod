package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BoozeFluids {
	
	public Map<String, Fluid> xml_mash = new HashMap<String, Fluid>();
	public Map<String, Fluid> xml_alcohol = new HashMap<String, Fluid>();
	
	public Fluid fluidAlcohol;
	
	public void init() {
		fluidAlcohol = new Fluid("alcohol").setLuminosity(0).setDensity(1100).setViscosity(1000).setTemperature(295);
		register();
	}
	
	public void register() {
		registerFluid(fluidAlcohol, "alcohol");
	}
	
	public static void registerFluid(Fluid paramFluid, String paramString)
	{
		FluidRegistry.registerFluid(paramFluid);
    	paramFluid = FluidRegistry.getFluid(paramString);
    }
}

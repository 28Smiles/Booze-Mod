package de.booze.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BoozeFluids {
	
	public Map<String, Fluid> xml_fluids = new HashMap<String, Fluid>();
	
	public Fluid fluidMash;
	
	public void init() {
		fluidMash = new Fluid("mash").setLuminosity(0).setDensity(1100).setViscosity(1000).setTemperature(295);
		register();
	}
	
	public void register() {
		registerFluid(fluidMash, "mash");
	}
	
	public static void registerFluid(Fluid paramFluid, String paramString)
	{
		FluidRegistry.registerFluid(paramFluid);
    	paramFluid = FluidRegistry.getFluid(paramString);
    }
}

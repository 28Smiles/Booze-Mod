package de.booze.proxy;

import java.util.Set;

import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.BoozeMod;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit() {
		
	}
	
	@Override
	public void init() {
		BoozeMod.tiles.registerClient();
	}
	
	@Override
	public void postinit() {
		
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre paramPre) {
		if (paramPre.map.getTextureType() == 0) {
			registerFluidIcons(BoozeMod.fluids.fluidAlcohol, paramPre.map);
			String[] keys = BoozeMod.fluids.xml_mash.keySet().toArray(new String[BoozeMod.fluids.xml_mash.size()]);
			for(String id : keys) {
				Fluid fluid = BoozeMod.fluids.xml_mash.remove(id);
				String texturePath = id.substring(5);
				registerMashIcons(fluid, texturePath, paramPre.map);
				BoozeMod.fluids.xml_mash.put(id, fluid);
			}
			
			keys = BoozeMod.fluids.xml_alcohol.keySet().toArray(new String[BoozeMod.fluids.xml_alcohol.size()]);
			for(String id : keys) {
				Fluid fluid = BoozeMod.fluids.xml_alcohol.remove(id);
				String texturePath = id.substring(8);
				texturePath = texturePath.replace(".", "_");
				registerAlcoholIcons(fluid, texturePath, paramPre.map);
				BoozeMod.fluids.xml_alcohol.put(id, fluid);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post paramPost) {
		setFluidIcons(BoozeMod.fluids.fluidAlcohol);
		String[] keys = BoozeMod.fluids.xml_mash.keySet().toArray(new String[BoozeMod.fluids.xml_mash.size()]);
		for(String id : keys) {
			Fluid fluid = BoozeMod.fluids.xml_mash.remove(id);
			setFluidIcons(fluid);
			BoozeMod.fluids.xml_mash.put(id, fluid);
		}
		
		keys = BoozeMod.fluids.xml_alcohol.keySet().toArray(new String[BoozeMod.fluids.xml_alcohol.size()]);
		for(String id : keys) {
			Fluid fluid = BoozeMod.fluids.xml_alcohol.remove(id);
			setFluidIcons(fluid);
			BoozeMod.fluids.xml_alcohol.put(id, fluid);
		}
	}

	public static void registerFluidIcons(Fluid paramFluid, IIconRegister paramIIconRegister)
	{
	    String str = StringHelper.titleCase(paramFluid.getName());
	    IconRegistry.addIcon("Fluid" + str, "booze:fluid/Fluid_" + str + "_Still", paramIIconRegister);
	    IconRegistry.addIcon("Fluid" + str + 1, "booze:fluid/Fluid_" + str + "_Flow", paramIIconRegister);
	}
	
	public static void registerMashIcons(Fluid paramFluid, String name, IIconRegister paramIIconRegister)
	{
	    String str = StringHelper.titleCase(paramFluid.getName());
	    IconRegistry.addIcon("Fluid" + str, "booze:fluid/mash/Fluid_" + name + "_Still", paramIIconRegister);
	    IconRegistry.addIcon("Fluid" + str + 1, "booze:fluid/mash/Fluid_" + name + "_Flow", paramIIconRegister);
	}
	
	public static void registerAlcoholIcons(Fluid paramFluid, String name, IIconRegister paramIIconRegister)
	{
	    String str = StringHelper.titleCase(paramFluid.getName());
	    IconRegistry.addIcon("Fluid" + str, "booze:fluid/alcohol/Fluid_" + name + "_Still", paramIIconRegister);
	    IconRegistry.addIcon("Fluid" + str + 1, "booze:fluid/alcohol/Fluid_" + name + "_Flow", paramIIconRegister);
	}

	public static void setFluidIcons(Fluid paramFluid)
	{
		String str = StringHelper.titleCase(paramFluid.getName());
		paramFluid.setIcons(IconRegistry.getIcon("Fluid" + str), IconRegistry.getIcon("Fluid" + str, 1));
	}
}

package de.booze.api;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.booze.api.multiblock.IMultiBlockRegistry;
import de.booze.api.multiblock.MultiBlockRegistry;
import de.booze.api.multiblock.events.MultiBlockEventHandler;

@Mod(modid = BoozeAPI.MODID, name="BoozeAPI", version = BoozeAPI.VERSION, dependencies="before:booze;")
public class BoozeAPI {
	
	public static final String MODID = "booze-api";
    public static final String VERSION = "1.0";
    
    private MultiBlockEventHandler multiblock = new MultiBlockEventHandler();
    
    public static IMultiBlockRegistry registry = new MultiBlockRegistry();
    
    public static Logger log;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	log = event.getModLog();
    	MinecraftForge.EVENT_BUS.register(multiblock);
    	FMLCommonHandler.instance().bus().register(multiblock);
    }
}

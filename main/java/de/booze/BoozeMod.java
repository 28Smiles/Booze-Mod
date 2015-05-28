package de.booze;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import de.booze.handlers.BoozeBlocks;
import de.booze.handlers.BoozeConfigHandler;
import de.booze.handlers.BoozeFluids;
import de.booze.handlers.BoozeItems;
import de.booze.handlers.BoozeTiles;
import de.booze.handlers.GuiHandler;
import de.booze.network.PacketHandler;
import de.booze.proxy.CommonProxy;

@Mod(modid = BoozeMod.MODID, version = BoozeMod.VERSION)
public class BoozeMod {
	
	public static final String MODID = "booze";
    public static final String VERSION = "0.0.1";
    
    @Instance(BoozeMod.MODID)
    public static BoozeMod INSTANCE;
    
    public static final String CLIENT_PROXY = "de.booze.proxy.ClientProxy";
    public static final String SERVER_PROXY = "de.booze.proxy.CommonProxy";
    
    @SidedProxy(clientSide = BoozeMod.CLIENT_PROXY, serverSide = BoozeMod.SERVER_PROXY)
    public static CommonProxy proxy;
    
    public static BoozeConfigHandler config_handler = new BoozeConfigHandler();
    public static PacketHandler packet_handler = new PacketHandler();
    public static GuiHandler gui_handler = new GuiHandler();
    
    public static BoozeBlocks blocks = new BoozeBlocks();
    public static BoozeItems items = new BoozeItems();
    public static BoozeTiles tiles = new BoozeTiles();
    public static BoozeFluids fluids = new BoozeFluids();
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config_handler.initCfg(config);
    	
    	items.init();
    	blocks.init();
    	fluids.init();
    	tiles.init();
    	proxy.preinit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	items.register();
    	blocks.register();
    	fluids.register();
    	tiles.register();
    	packet_handler.init();
    	proxy.init();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, gui_handler);
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	proxy.postinit();
    }
}

package de.booze;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidStack;
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
import de.booze.handlers.CraftingHandler;
import de.booze.handlers.GuiHandler;
import de.booze.proxy.CommonProxy;
import de.booze.xml.XMLItemCreator;
import de.booze.xml.XMLReader;

@Mod(modid = BoozeMod.MODID, version = BoozeMod.VERSION, dependencies="required-after:CoFHCore@[1.7.10R3.0.0,); after:IC2; after:ThermalExpansion;")
public class BoozeMod {
	
	public static final String MODID = "booze";
    public static final String VERSION = "0.6";
    
    @Instance(BoozeMod.MODID)
    public static BoozeMod INSTANCE;
    
    public static final String CLIENT_PROXY = "de.booze.proxy.ClientProxy";
    public static final String SERVER_PROXY = "de.booze.proxy.CommonProxy";
    
    @SidedProxy(clientSide = BoozeMod.CLIENT_PROXY, serverSide = BoozeMod.SERVER_PROXY)
    public static CommonProxy proxy;
    
    public static BoozeConfigHandler config_handler = new BoozeConfigHandler();
    public static GuiHandler gui_handler = new GuiHandler();
    
    public static BoozeBlocks blocks = new BoozeBlocks();
    public static BoozeItems items = new BoozeItems();
    public static BoozeTiles tiles = new BoozeTiles();
    public static BoozeFluids fluids = new BoozeFluids();
    
    public static Logger log;
    
    public static CreativeTabs tabCommon;
    
    public static boolean ic2Enabled = false;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	log = event.getModLog();
    	
    	try {
			Class.forName("ic2.api.Direction");
			log.info("[Booze]: IC2 found!");
			ic2Enabled = true;
		} catch (ClassNotFoundException e1) {
			log.warn("[Booze]: IC2 not found!");
			ic2Enabled = false;
		}
    	
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config_handler.initCfg(config);
    	tabCommon = new CreativeTabs("Booze") {
    		@Override
    		public Item getTabIconItem() {
    			return Items.glass_bottle;
    		}
    	};
    	fluids.init();
    	items.init();
    	blocks.init();
    	tiles.init();
    	try {
			XMLReader.init();
			XMLItemCreator.createMashes(XMLReader.mash);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	proxy.preinit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	tiles.register();
    	proxy.init();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, gui_handler);
    	MinecraftForge.EVENT_BUS.register(proxy);
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	proxy.postinit();
    }
}

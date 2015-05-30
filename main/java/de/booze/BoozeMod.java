package de.booze;

import ic2.api.item.IC2Items;
import ic2.core.Ic2Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(modid = BoozeMod.MODID, version = BoozeMod.VERSION, dependencies="required-after:IC2@[2.2,); required-after:CoFHCore@[1.7.10R3.0.0,); before:ThermalExpansion;")
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
    
    public static CreativeTabs tabCommon;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config_handler.initCfg(config);
    	tabCommon = new CreativeTabs("Booze") {
    		@Override
    		public Item getTabIconItem() {
    			return Ic2Items.mugEmpty.getItem();
    		}
    	};
    	fluids.init();
    	items.init();
    	blocks.init();
    	tiles.init();
    	proxy.preinit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	tiles.register();
    	packet_handler.init();
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

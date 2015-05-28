package de.booze.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import de.booze.BoozeMod;

public class PacketHandler {
	
	public static final SimpleNetworkWrapper SNW = NetworkRegistry.INSTANCE.newSimpleChannel(BoozeMod.MODID);
	
	public static void init() {
		
	}
}

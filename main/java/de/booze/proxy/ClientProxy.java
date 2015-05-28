package de.booze.proxy;

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
}

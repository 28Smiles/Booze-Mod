package de.booze.handlers;

import net.minecraftforge.common.config.Configuration;

public class BoozeConfigHandler {
	
	public static boolean ic2EnergyOverflow;
	
	public void initCfg(Configuration cfg) {
		ic2EnergyOverflow = cfg.getBoolean("ic2.energy.overlow", "IC2", true, "true for Performance increase");
	}
}

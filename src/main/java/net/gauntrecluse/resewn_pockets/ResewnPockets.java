package net.gauntrecluse.resewn_pockets;

import net.fabricmc.api.ModInitializer;

import net.gauntrecluse.resewn_pockets.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResewnPockets implements ModInitializer {
	public static final String MOD_ID = "resewn_pockets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing, give us a moment to figure out how and when to sew your players' pockets.");
		LOGGER.warn("Resewn Pockets is still in very early development! Here be dragons!");
		Configs.init();
	}
}
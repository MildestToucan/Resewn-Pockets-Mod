package net.gauntrecluse.resewn_pockets.config;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;

public class Configs {

    public static ResewnConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ResewnConfig::new, RegisterType.SERVER);

    public static void init() {

    }

}
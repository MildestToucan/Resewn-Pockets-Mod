package net.gauntrecluse.resewn_pockets.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.gauntrecluse.resewn_pockets.ResewnPockets;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ResewnConfig extends Config {


    public ValidatedMap<String, Integer> itemByCount = (new ValidatedMap.Builder<String, Integer>())
            .keyHandler(new ValidatedString())
            .valueHandler(new ValidatedInt())
            .defaults(Map.of()).build();

    public ResewnConfig() {
        super(ResourceLocation.fromNamespaceAndPath(ResewnPockets.MOD_ID, "config"),
                "Resewn Pockets", "Resewn Configs", "General Config"
        );
    }
}

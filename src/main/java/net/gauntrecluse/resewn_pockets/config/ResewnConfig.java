package net.gauntrecluse.resewn_pockets.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedCondition;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.gauntrecluse.resewn_pockets.ResewnPockets;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ResewnConfig extends Config {


    public boolean DEBUG_alwaysFalse = false;
    public boolean DEBUG_alwaysTrue = false;


    public boolean WIP_TOGGLER_checkWhenCreative = false;
    public boolean WIP_TOGGLER_includeShulkerInChecks = false; //TODO: Make this work and set it to default true
    public boolean WIP_TOGGLER_includeBundleInChecks = false; //TODO: See above, maybe make the two bundled in one setting?(prolly not)




    public ValidatedBoolean TOGGLER_itemByCountToggle = new ValidatedBoolean(true);

    public ValidatedCondition<Map<String, ? extends Integer>> itemByCount = (
            (new ValidatedMap.Builder<String, Integer>())
            .keyHandler(new ValidatedString())
            .valueHandler(new ValidatedInt())
            .defaults(Map.of()).build()
    ).toCondition(TOGGLER_itemByCountToggle, Map::of);

    public ResewnConfig() {
        super(ResourceLocation.fromNamespaceAndPath(ResewnPockets.MOD_ID, "resewn_config"), "Resewn Pockets");
    }
}
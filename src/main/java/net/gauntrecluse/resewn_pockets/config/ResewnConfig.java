package net.gauntrecluse.resewn_pockets.config;

import me.fzzyhmstrs.fzzy_config.annotations.*;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.gauntrecluse.resewn_pockets.ResewnPockets;
import net.minecraft.resources.ResourceLocation;

import java.lang.Integer;
import java.util.Map;

/**
 * Naming convention for this config follows certain conventions based on a prefix system: <br>
 * {@code WIP_} => Not complete or implemented. <br>
 * {@code DEBUG_} => Used for developing and bug hunting purposes. <br>
 * {@code TOGGLER_} => Boolean value that toggles a logic check on or off. <br>
 * {@code lack of prefix} => Typically, directly related to a logic check's elements like {@code itemByCount}.
 */
public class ResewnConfig extends Config {

    @RequiresAction(action = Action.RESTART)
    @Comment("Highest priority, makes mod always think a player shouldn't hold any item.")
    public boolean DEBUG_alwaysFalse = false;

    @RequiresAction(action = Action.RESTART)
    @Comment("Second highest priority, makes mod always think a player should hold any item.")
    public boolean DEBUG_alwaysTrue = false;

    @RequiresAction(action = Action.RESTART)
    @Comment("When set to false, makes the mod ignore creative players, priority position: 3rd")
    public boolean TOGGLER_checkWhenCreative = false;

    @Comment("Currently unimplemented options, do not do anything.")
    public boolean WIP_TOGGLER_includeShulkerInChecks = false; //TODO: Make this work and set it to default true
    public boolean WIP_TOGGLER_includeBundleInChecks = false; //TODO: See above, maybe make the two bundled in one setting?(prolly not)



    @RequiresAction(action = Action.RESTART)
    @Comment("Toggles the itemByCount logic checks to make it easier to toggle checks, priority position: 4th")
    public ValidatedBoolean TOGGLER_itemByCountToggle = new ValidatedBoolean(true);


    @RequiresAction(action = Action.RESTART)
    @Inline
    @Comment(value = """
            Pairs a String (key) with an Integer (value). The String should correspond to an Item's ResourceLocation.
            For instance minecraft:diamond for a Diamond, or modid:moditem for a modded item. Here is an example of how to format the input:
            itemByCount = [ ["minecraft:diamond", 10], ["minecraft:gold_ingot", 10] ] (do not add the backslashes in your config.)
            The number corresponds to how much of that item the player is allowed to have in their inventory. Put 0 if you don't want them to hold any.
            Priority position: 5th""")
    public ValidatedMap<String, ? extends Integer> itemByCount =
            (new ValidatedMap.Builder<String, Integer>())
                    .keyHandler(new ValidatedString())
                    .valueHandler(new ValidatedInt())
                    .defaults(Map.of())
                    .build();


    public ResewnConfig() {
        super(ResourceLocation.fromNamespaceAndPath(ResewnPockets.MOD_ID, "resewn_config"));
    }
}
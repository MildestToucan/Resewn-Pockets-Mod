package net.gauntrecluse.resewn_pockets;

import net.gauntrecluse.resewn_pockets.config.Configs;
import net.gauntrecluse.resewn_pockets.mixin.InventoryMixin;
import net.gauntrecluse.resewn_pockets.mixin.ItemStackMixin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

/**
 * Class that uses the mod's config and freely encourages external mods' mixins to modify
 * the conditions applied to players picking up items. <br>
 * This class's methods are used by Mixins to add to the vanilla item logic, such that the overall logic will prevent
 * the player from getting the item if either Resewn Pockets or Vanilla checks fail.
 */
public class SewingPatterns {

    public static boolean DEBUG_ALWAYS_FALSE = false; //TODO: make sure this is set to false before any release is made.
    public static boolean DEBUG_ALWAYS_TRUE = false;


    /**
     * @see InventoryMixin
     * @return true if it should pick up normally, false if item shouldn't be picked up
     */
    public static boolean canPickUp(Player player, ItemStack itemStack) {
        ResewnPockets.LOGGER.debug("canPickUp triggered.");
        return sharedLogic(itemStack, player);
    }

    /**
     * @see ItemStackMixin
     * @return true if it should ignore the item, false if item should be thrown.
     */
    public static boolean mayHold(ItemStack itemStack, ServerPlayer player) {
        ResewnPockets.LOGGER.debug("mayHold triggered.");
        return sharedLogic(itemStack, player);
    }


    /**
     * This method is returned by both {@code #mayHold} and {@code #canPickUp} <br>
     * It will always return true by default if none of the criteria cause an early return.
     */
    public static boolean sharedLogic(ItemStack itemStack, Player player) {
        if(DEBUG_ALWAYS_FALSE) {
            ResewnPockets.LOGGER.warn("DEBUG_ALWAYS_FALSE is ON!");
            return false;
        }

        if(DEBUG_ALWAYS_TRUE) {
            ResewnPockets.LOGGER.warn("DEBUG_ALWAYS_TRUE is ON!");
            return true;
        }


        ResewnPockets.LOGGER.warn("CHECKING: {}", itemStack.getItem());
        if(itemByCount.isEmpty()) {
            ResewnPockets.LOGGER.warn("itemByCount is empty!");
        } else if(itemByCount.containsKey(itemStack.getItem().toString())){
            return player.getInventory().countItem(itemStack.getItem()) <= itemByCount.get(itemStack.getItem().toString());
        }

        return true;
    }

    public static Map<String, ? extends Integer> itemByCount = Configs.CONFIG.itemByCount.get();

}

package net.gauntrecluse.resewn_pockets;

import net.gauntrecluse.resewn_pockets.config.Configs;
import net.gauntrecluse.resewn_pockets.mixin.InventoryMixin;
import net.gauntrecluse.resewn_pockets.mixin.ResewnItemStackMixin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

/**
 * Class that uses the mod's config and freely encourages external mods' mixins to modify
 * the conditions applied to players picking up items. <br>
 * This class's methods are used by Mixins to add to the vanilla item logic, such that the overall logic will prevent
 * the player from getting the item if either Resewn Pockets or Vanilla checks fail.
 */
public class SewingPatterns {

    //PS: I am not responsible for heart attacks or other injuries/illness resulting from viewing my atrocious code.

    public static boolean DEBUG_ALWAYS_FALSE = Configs.CONFIG.DEBUG_alwaysFalse;
    public static boolean DEBUG_ALWAYS_TRUE = Configs.CONFIG.DEBUG_alwaysTrue;
    public static Map<String, ? extends Integer> itemByCount = Configs.CONFIG.itemByCount.get();



    public static int getItemCountInInventory(ServerPlayer player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();

        return inventory.countItem(itemStack.getItem());
    }

    public static int getItemCountInInventory(ServerPlayer player, Item item) {
        Inventory inventory = player.getInventory();

        return inventory.countItem(item);
    }


    public static String getItemNameFromStack(ItemStack itemStack) {
        return itemStack.getItem().toString();
    }

    public static int getItemMaxCount(ItemStack itemStack) {return getItemMaxCount(itemStack.getItem().toString());}
    public static int getItemMaxCount(Item item) {return getItemMaxCount(item.toString());}
    public static int getItemMaxCount(String itemName) {
        return itemByCount.get(itemName);
    }


    public static int makeDroppingCalcs(ItemStack itemStack, ServerPlayer player) {
        int maxCountForThis = getItemMaxCount(itemStack);
        int totalCountInInventory = getItemCountInInventory(player, itemStack);
        int diff = totalCountInInventory - maxCountForThis;

        if(diff > 0) {
            //Give the excess amount.
            return diff;
        }
        //Tell whatever is using this that the player is holding *less* than the limit.
        return -1;
    }



    /*====== INJECTED LOGIC ======*/

    /**@see InventoryMixin*/
    public static boolean canPickUp(ServerPlayer serverPlayer, ItemStack itemStack) {
        ResewnPockets.LOGGER.debug("canPickUp triggered.");

        if(!pickUpLogic(itemStack, serverPlayer)) return false; //give non-shared logic priority over shared logic.

        return sharedLogic(itemStack, serverPlayer);
    }

    /**@see ResewnItemStackMixin */
    public static boolean mayHold(ItemStack itemStack, ServerPlayer player) {
        ResewnPockets.LOGGER.debug("mayHold triggered.");

        if(!holdingLogic(itemStack, player)) return false;

        return sharedLogic(itemStack, player);
    }


    public static boolean pickUpLogic(ItemStack itemStack, ServerPlayer serverPlayer) {

        return true;
    }


    public static boolean holdingLogic(ItemStack itemStack, ServerPlayer player) {


        return true;
    }




    public static boolean sharedLogic(ItemStack itemStack, ServerPlayer player) {
        if(DEBUG_ALWAYS_FALSE) {
            ResewnPockets.LOGGER.warn("DEBUG_ALWAYS_FALSE is ON!");
            return false;
        }

        if(DEBUG_ALWAYS_TRUE) {
            ResewnPockets.LOGGER.warn("DEBUG_ALWAYS_TRUE is ON!");
            return true;
        }


//        ResewnPockets.LOGGER.warn("CHECKING: {}", itemStack.getItem());
        if(itemByCount.isEmpty()) {
//            ResewnPockets.LOGGER.warn("itemByCount is empty!"); NOTE: disabled to reduce log bloat during slotMixin test.
        } else if(itemByCount.containsKey(itemStack.getItem().toString())) { //Note, not sure if the toString call is needed.
            ResewnPockets.LOGGER.warn("itemByCount doesn't contain this item!");
        } else {
            int diff = makeDroppingCalcs(itemStack, player);
            if(diff == -1) {
                ResewnPockets.LOGGER.warn("Player doesn't carry above the item limit!");
            } else {

            }
        }

        return true;
    }


    //Exists for development logic testing in mixins to ensure they work well.
    public static boolean testLogic(ItemStack itemStack) {
        ResewnPockets.LOGGER.warn("testLogic triggered!");
        ResewnPockets.LOGGER.warn("testLogic currently always returns false");
        ResewnPockets.LOGGER.warn("testLogic received stack of item {}", itemStack.getItem());
        return false;
    }
}

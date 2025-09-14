package net.gauntrecluse.resewn_pockets;

import net.gauntrecluse.resewn_pockets.config.Configs;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Class containing the operations and logic for the mod's operations injected to vanilla via Mixins. <br>
 * Certain parts may be delegated to other classes in the case of a very large amount of methods.
 */
public class ModOperations {
    private static final Logger LOGGER = ResewnPockets.LOGGER; //Passing the logger here for convenience.

    //? Possibly make these final later
    public static boolean TOGGLER_checkWhenCreative = Configs.CONFIG.TOGGLER_checkWhenCreative;
    public static boolean DEBUG_alwaysTrue = Configs.CONFIG.DEBUG_alwaysTrue;
    public static boolean DEBUG_alwaysFalse = Configs.CONFIG.DEBUG_alwaysFalse;

    public static Map<String, ? extends Integer> itemByCount = Configs.CONFIG.itemByCount;
    public static boolean TOGGLER_itemByCount = Configs.CONFIG.TOGGLER_itemByCountToggle.get();

    public static void dropStacks(ServerPlayer player, NonNullList<ItemStack> stacks, int excess) {
        Inventory inventory = player.getInventory();

        for(ItemStack stack : stacks) {
            player.drop(stack, false,false);
        }
        clearNOfItem(inventory, excess, stacks.getFirst().getItem());
    }

    public static void genericLog() {
        LOGGER.debug("genericLog called");
    }



    /**
     * @return the excess or lackthereof of a given item in the inventory. If the result is 0 or less, we know the limit wasn't met.
     * */
    public static int getExcess(Item item, Inventory inventory, int itemLimit) {
        LOGGER.info("getExcess called.");
        int itemCountInInventory = 0;
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack countingStack = inventory.getItem(i);
            if(countingStack.is(item)) {
                itemCountInInventory += countingStack.getCount();
            }
        }
        LOGGER.info("counted {} of target item in inventory", itemCountInInventory);
        LOGGER.info("returning {}", (itemCountInInventory - itemLimit));
        return itemCountInInventory - itemLimit;
    }

    public static int getExcessIncludeInput(ItemStack inputStack, Inventory inventory, int itemLimit) {
        LOGGER.info("getExcess with ItemStack inputStack called.");
        Item item = inputStack.getItem();
        int itemCountInInventory = inputStack.getCount();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack countingStack = inventory.getItem(i);
            if(countingStack.is(item)) {
                itemCountInInventory += countingStack.getCount();
            }
        }
        LOGGER.info("counted {} of target item in inventory and incoming stack", itemCountInInventory);
        LOGGER.info("returning {}", (itemCountInInventory - itemLimit));
        return itemCountInInventory - itemLimit;
    }

    /**
     * Iterates through inventory slots, clearing a target item when it finds it until it has removed a certain amount of it. <br>
     * The purpose of this method is that in the context of dropping a variable amount of excess items over a variable
     * limit, we may run into the issue of being unable to find a single stack in the inventory we can "take" from by
     * shrinking it after dropping an appropriate stack, this could happen if the excess is of several stacks or if the
     * excess is spread across multiple stacks in the inventory. <br>
     * When used in combination with {@link #createDropStacks(int, ItemStack)}, we can create a proper list of stacks
     * to drop and then use this to remove an amount of items equal to the amount we're dropping.
     * @param n the number of the item that must be cleared, e.g. put 20 to clear 20 of the item.
     *          must always be above 0 to prevent issues.
     * */
    public static void clearNOfItem(Inventory inventory, int n, Item item) { //! Maybe throw exception if n <= O?
        for(ItemStack stack : inventory.items) {
            if(!stack.is(item)) continue;
            if(stack.getCount() < n) {
                n -= stack.getCount();
                inventory.removeItem(stack);
            } else if(stack.getCount() > n) {
                stack.shrink(n);
                return;
            } else {
                inventory.removeItem(stack);
                return;
            }
        }
        if(item instanceof ArmorItem) {
            for(ItemStack stack : inventory.armor) {
                if(!stack.is(item)) continue;
                if(stack.getCount() < n) {
                    n -= stack.getCount();
                    inventory.removeItem(stack);
                } else if(stack.getCount() > n) {
                    stack.shrink(n);
                    return;
                } else {
                    inventory.removeItem(stack);
                    return;
                }
            }
        }

        ItemStack offHandStack = inventory.offhand.getFirst();
        if(offHandStack.is(item)) {
            if(offHandStack.getCount() < n) {
                n -= offHandStack.getCount();
                inventory.removeItem(offHandStack);
            } else if(offHandStack.getCount() > n) {
                offHandStack.shrink(n);
            } else {
                inventory.removeItem(offHandStack);
            }
        }
    }


    /**
     * Creates a {@link NonNullList} of {@code ItemStack} objects organized to all be below the max stack limit for
     * the given item, allowing to drop them without going over that limit. <br>
     * @implNote The returned {@code ItemStack}s are created by copying the {@code stack} parameter,
     * it may be better to instead create stacks based on the {@code Item} instead to avoid
     * possibly copying properties accidentally.
     * @param stack the ItemStack to use as a base for the returned stacks.
     * @param excess the total amount to drop, typically should be the excess calculated in a prior operation.
     * */
    public static NonNullList<ItemStack> createDropStacks(int excess, ItemStack stack) {
        int maxStackSize = stack.getMaxStackSize();
        if(excess <= maxStackSize) {
            return NonNullList.of(stack.copyWithCount(excess));
        }
        NonNullList<ItemStack> list = NonNullList.create();

        int remainder = excess % maxStackSize;
        if(remainder != 0) {
            list.add(stack.copyWithCount(remainder));
        }
        int mutExcess = excess - remainder;

        while(mutExcess > 0) {
            list.add(stack.copyWithCount(maxStackSize));
            mutExcess -= maxStackSize;
        }
        return list;
    }

    /**
     * Shorthand method that drops the {@code stack} parameter in a generic way.
     * */
    public static void dropItemStack(ServerPlayer player, ItemStack stack) {
        Inventory inventory = player.getInventory();
        player.drop(stack, false, false);
        inventory.removeItem(stack);
    }


    /**
     * Based on config-driven logic, we can return whether a given player should be allowed to have
     * that stack in their inventory. This can be used to prevent a pickup, or to conditionally trigger
     * operations to remove inappropriate items from the inventory.
     * @return Whether the player should be allowed to have the ItemStack based on custom rules.
     */
    public static boolean shouldHave(ServerPlayer player, ItemStack stack) {
        LOGGER.debug("ModOperations#shouldHave triggered!");
        LOGGER.info("Item being checked: {}", stack.getItem());
        if(DEBUG_alwaysFalse) {
            LOGGER.warn("DEBUG_alwaysFalse is true, shouldHave returning false");
            return false;
        }
        if(DEBUG_alwaysTrue) {
            LOGGER.warn("DEBUG_alwaysTrue is true, shouldHave returning true");
            return true;
        }
        if(!TOGGLER_checkWhenCreative && player.isCreative()) {
            LOGGER.warn("CheckWhenCreative if off, and the checked player is in creative; returning true");
            return true;
        }
        Item item = stack.getItem();

        if(TOGGLER_itemByCount) {
            if(itemByCount.containsKey(item.toString())) {
                int itemLimit = itemByCount.get(item.toString());
                int excess = getExcessIncludeInput(stack, player.getInventory(), itemLimit);
                if(excess > 0) {
                    LOGGER.warn("shouldHave returning to false due to checked item being above the allowed limit");
                    return false;
                }
                LOGGER.info("Checked item was counted to be below the item limit");
            }
        }

        LOGGER.info("shouldHave returned true due to no early false return.");
        return true;
    }
}
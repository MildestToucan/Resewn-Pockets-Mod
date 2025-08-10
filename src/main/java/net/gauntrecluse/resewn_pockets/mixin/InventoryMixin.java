package net.gauntrecluse.resewn_pockets.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.gauntrecluse.resewn_pockets.SewingPatterns;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

/**
 * This Mixin uses the {@link SewingPatterns} class for its logic. <br>
 * in order to modify what this mixin does, you may either mix into the SewingPatterns class, or use the mod's config. <br>
 * @author GauntRecluse
 * @since initial development
 */
@Debug(export = true)
@Mixin(value = Inventory.class)
public abstract class InventoryMixin implements Container, Nameable {
    @Shadow @Final public Player player;

    @WrapOperation(
            method = "add(ILnet/minecraft/world/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 0))
    private boolean inventoryAddSew(ItemStack itemStack, Operation<Boolean> original) {
        if(player instanceof ServerPlayer serverPlayer) {
            return original.call(itemStack) || !SewingPatterns.canPickUp(serverPlayer, itemStack);
        }
        return original.call(itemStack);
    }
}




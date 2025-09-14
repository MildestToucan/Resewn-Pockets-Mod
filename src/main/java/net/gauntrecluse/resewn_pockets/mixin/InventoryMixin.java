package net.gauntrecluse.resewn_pockets.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.gauntrecluse.resewn_pockets.ModOperations;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true)
@Mixin(value = Inventory.class)
public abstract class InventoryMixin implements Container, Nameable {
    @Shadow @Final public Player player;

    //During item pickup checks, if the modded logic returns false, tells the game the itemstack is empty, making the pickup fail.
    @WrapOperation(
            method = "add(ILnet/minecraft/world/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 0),
            slice = @Slice(
                    from = @At("HEAD"), //explicitly added default value for clarity.
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamaged()Z")
            )
    )
    private boolean inventoryAddSew(ItemStack itemStack, Operation<Boolean> original) {
        if(player instanceof ServerPlayer serverPlayer) {
            return original.call(itemStack) || !ModOperations.shouldHave(serverPlayer, itemStack);
        }
        return original.call(itemStack);
    }
}
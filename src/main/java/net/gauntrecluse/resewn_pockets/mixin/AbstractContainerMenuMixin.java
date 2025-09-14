package net.gauntrecluse.resewn_pockets.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import net.gauntrecluse.resewn_pockets.ModOperations;
import net.gauntrecluse.resewn_pockets.ResewnPockets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(value = AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {


    //This prevents the stack from being snapped to the player's inventory when the menu is closed with the stack still carried by the cursor.
    @WrapOperation(method = "removed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isAlive()Z"))
    private boolean controlInventoryPlaceBack(Player player, Operation<Boolean> original, @Local(ordinal = 0)ItemStack itemStack) {
        if(player instanceof ServerPlayer serverPlayer) {
            return original.call(player) && ModOperations.shouldHave(serverPlayer, itemStack);
        }
        return original.call(player);
    }
}
package net.gauntrecluse.resewn_pockets.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.gauntrecluse.resewn_pockets.SewingPatterns;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(value = AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {

    /**
     * This aims to only place back the cursor's stack into the inventory upon the inventory closing IF the mod's holding logic allows it. <br>
     * This is meant to complement {@link SlotMixin} which does not cover this case.
     */
    //TODO: Figure out whether to make the stack be dropped or do something else.
    @WrapOperation(method = "removed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isAlive()Z"))
    private boolean controlInventoryPlaceBack(Player player, Operation<Boolean> original, @Local(ordinal = 0)ItemStack itemStack) {
        //Note: the vanilla code already confirmed the player is an instance of ServerPlayer, casting should be safe.
        return original.call(player) && SewingPatterns.testLogic(itemStack);
    }
}
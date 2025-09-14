package net.gauntrecluse.resewn_pockets.mixin;

import net.gauntrecluse.resewn_pockets.ModOperations;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin {

    @Inject(method = "quickMoveStack", at = @At(value = "HEAD"))
    private void injectLogAtHead(Player player, int i, CallbackInfoReturnable<ItemStack> cir) {
        ModOperations.genericLog();
    }
}
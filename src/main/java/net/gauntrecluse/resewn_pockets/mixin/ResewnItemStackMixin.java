package net.gauntrecluse.resewn_pockets.mixin;


import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Debug(export = true)
@Mixin(value = ItemStack.class) //Renamed mixin file to prevent confusion with Fabric mixins of the same name.
public abstract class ResewnItemStackMixin implements DataComponentHolder, FabricItemStack {

    @Inject(
            method = "inventoryTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;",
                    ordinal = 1
            ), cancellable = true
    )
    private void inventoryTickInjectSew(Level level, Entity entity, int i, boolean bl, CallbackInfo ci) {
    }
}

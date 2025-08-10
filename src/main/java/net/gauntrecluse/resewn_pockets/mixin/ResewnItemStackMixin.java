package net.gauntrecluse.resewn_pockets.mixin;


import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.gauntrecluse.resewn_pockets.SewingPatterns;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.server.level.ServerPlayer;
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

        if(entity instanceof ServerPlayer serverPlayerEntity){
            ItemStack thisItemStack = (ItemStack)(Object)this;
            if(!SewingPatterns.mayHold(thisItemStack, serverPlayerEntity)) {
                //TODO: Make this drop an item amount that is relative to excess rather than the whole stack.
                serverPlayerEntity.drop(thisItemStack, false, false);
                serverPlayerEntity.getInventory().removeItem(thisItemStack);

                /* As the ItemStack is removed,
                it may be ill-advised to not cancel the next tick call that takes the ItemStack as an argument. */
                ci.cancel();
            }
        }
    }
}

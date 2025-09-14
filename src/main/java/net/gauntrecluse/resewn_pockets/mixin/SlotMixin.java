package net.gauntrecluse.resewn_pockets.mixin;


import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.gauntrecluse.resewn_pockets.ModOperations;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Debug(export = true)
@Mixin(value = Slot.class)
public abstract class SlotMixin {


    @Shadow
    @Final
    public Container container;


    @WrapMethod(method = "mayPlace")
    private boolean resewnMayPlaceWrap(ItemStack itemStack, Operation<Boolean> original) {
        if(container instanceof Inventory inventory && inventory.player instanceof ServerPlayer serverPlayer) {
            return ModOperations.shouldHave(serverPlayer, itemStack);
        }

        return original.call(itemStack);
    }

}

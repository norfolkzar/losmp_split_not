package net.shadow.losmp.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "insertStack", at = @At("HEAD"), cancellable = true)
    private void awooga(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        PlayerInventory inv = (PlayerInventory)(Object)this;
        int[] useableSlots = {3, 4, 5};
        for (int slot : useableSlots) {
            ItemStack itemStack = inv.getStack(slot);
            if (!itemStack.isEmpty() && ItemStack.canCombine(itemStack, stack)) {
                int transferable = Math.min(stack.getCount(), itemStack.getMaxCount() - itemStack.getCount());
                if (transferable > 0) {
                    itemStack.increment(transferable);
                    stack.decrement(transferable);
                    if (stack.isEmpty()) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
        for (int slot : useableSlots) {
            if (inv.getStack(slot).isEmpty()) {
                inv.setStack(slot, stack.copy());
                stack.setCount(0);
                cir.setReturnValue(true);
                return;
            }
        }
        cir.setReturnValue(false);
    }

}
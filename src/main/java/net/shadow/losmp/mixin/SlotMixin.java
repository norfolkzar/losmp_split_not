package net.shadow.losmp.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Unique
    private boolean isSlotAllowedToUse(Slot slot) {
        int index = slot.getIndex();
        boolean isIndexWithinRange = ((index >= 3 && index <= 5) || (index > 35 && index < 40));
        return (!(slot.inventory instanceof PlayerInventory) || isIndexWithinRange);
    }

    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    private void awooga(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        Slot slot = (Slot) (Object) this;
        if ((player.currentScreenHandler instanceof PlayerScreenHandler)) {
            if (isSlotAllowedToUse(slot)) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void adooga(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Slot slot = (Slot)(Object)this;
        if (isSlotAllowedToUse(slot)) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canBeHighlighted", at = @At("HEAD"), cancellable = true)
    private void abooga(CallbackInfoReturnable<Boolean> cir) {
        Slot slot = (Slot)(Object)this;
        cir.setReturnValue(isSlotAllowedToUse(slot));
    }
}
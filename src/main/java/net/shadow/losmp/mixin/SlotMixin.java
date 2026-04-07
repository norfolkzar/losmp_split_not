package net.shadow.losmp.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.shadow.losmp.screen.SignalBoosterScreen;
import net.shadow.losmp.screen.SignalBoosterScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Final
    @Shadow
    public Inventory inventory;

    @Unique
    private boolean isSlotAllowedToUse(Slot slot) {
        int index = slot.getIndex();
        boolean isIndexWithinRange = ((index >= 3 && index <= 5) || (index > 35 && index < 40));
        return (!(slot.inventory instanceof PlayerInventory) || isIndexWithinRange);
    }

    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    private void awooga(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if(!player.hasPermissionLevel(2)) {
            Slot slot = (Slot) (Object) this;
            if ((player.currentScreenHandler instanceof PlayerScreenHandler)) {
                if (isSlotAllowedToUse(slot)) {
                    cir.setReturnValue(true);
                } else {
                    cir.setReturnValue(false);
                }
            }
            if (player.currentScreenHandler instanceof SignalBoosterScreenHandler) {
                if (slot.inventory instanceof SignalBoosterScreenHandler && slot.getIndex() == 4) {
                    cir.setReturnValue(false);
                } else cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void adooga(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Slot slot = (Slot)(Object)this;
        if(slot.inventory instanceof PlayerInventory && !((PlayerInventory) inventory).player.hasPermissionLevel(2)) {
            if (isSlotAllowedToUse(slot)) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "canBeHighlighted", at = @At("HEAD"), cancellable = true)
    private void abooga(CallbackInfoReturnable<Boolean> cir) {
        Slot slot = (Slot) (Object) this;
        if (slot.inventory instanceof PlayerInventory && !((PlayerInventory) inventory).player.hasPermissionLevel(2)) {
            cir.setReturnValue(isSlotAllowedToUse(slot));
        }
    }
}
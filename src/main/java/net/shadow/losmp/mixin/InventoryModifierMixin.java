package net.shadow.losmp.mixin;

import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class InventoryModifierMixin {
    @Unique
    PlayerInventory self = (PlayerInventory) (Object) this;
    @Inject(method = "isValidHotbarIndex",at=@At("HEAD"), cancellable = true)
    private static void isValidHotbarIndexThree(int slot, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(slot >= 3 && slot < 6);
    }

    @Inject(method = "scrollInHotbar",at=@At("HEAD"))
    private void scrollInHotbarThree(double scrollAmount, CallbackInfo ci) {
        if(!self.player.hasPermissionLevel(2)) {
            int i = (int) Math.signum(scrollAmount);
            while (self.selectedSlot - i < 3) {
                self.selectedSlot += 3;
            }
            while (self.selectedSlot - i > 5) {
                self.selectedSlot -= 3;
            }
        }
    }

    @Inject(method = "getEmptySlot",at=@At("HEAD"), cancellable = true)
    private void threeSlotIsEmpty(CallbackInfoReturnable<Integer> cir) {
        if (!self.player.hasPermissionLevel(2)) {
            cir.setReturnValue(-1);
            for (int i = 3; i < 6; ++i) {
                if ((self.main.get(i)).isEmpty()) {
                    cir.setReturnValue(i);
                }
            }
        }
    }
}

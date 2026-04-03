package net.shadow.losmp.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "isItemBarVisible", at = @At("HEAD"), cancellable = true)
    private void awoogae(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item item = stack.getItem();
        if (item.isFood()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getItemBarStep", at = @At("HEAD"), cancellable = true)
    private void awooga(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem().isFood()) {
            int max = 200*60;
            int damage = stack.getOrCreateNbt().getInt("losmp.food_decay");
            int step = Math.round(13.0F - (damage * 13.0F / max));
            cir.setReturnValue(step);
        }
    }
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void awooga(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        Item item = stack.getItem();
        if (item.isFood() && !world.isClient) {
            var nbt = stack.getOrCreateNbt();
            int age = nbt.getInt("losmp.food_decay");
            age++;
            nbt.putInt("losmp.food_decay", age);
            if (age >= 200*60) {
                stack.decrement(stack.getCount());
            }
        }
    }
}


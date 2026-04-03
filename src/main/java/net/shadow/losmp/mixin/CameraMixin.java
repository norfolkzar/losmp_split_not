package net.shadow.losmp.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.shadow.losmp.config.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(method = "update", at = @At("TAIL"))
    private void applyShake(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if ((focusedEntity instanceof PlayerEntity player)) {
            if (ModConfigs.isGyroZeppeliWorking.equals(false)) {
                float time = player.age + tickDelta;
                float intensity = 0.05f;
                float offsetX = (float) Math.sin(time * 0.8) * intensity;
                float offsetY = (float) Math.cos(time * 1.1) * intensity;
                Camera camera = (Camera) (Object) this;
                ((CameraAccessor) camera).invokeMoveBy(offsetX, offsetY, 0);
            }
        }
    }
}
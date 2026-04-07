package net.shadow.losmp.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.shadow.losmp.registries.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render",at=@At(value = "HEAD"))
    private void awooga(DrawContext context, float tickDelta, CallbackInfo ci){
        InGameHud self = (InGameHud) (Object) this;
        var itemStack = ((InGameHudAccessor) self).clientAccessor().player.getInventory().getMainHandStack();
        if(itemStack.getItem() == ModItems.SHOTGUN){
            var number = itemStack.getOrCreateNbt().get("losmp.ammo");
            //context.drawText(((InGameHudAccessor) self).clientAccessor().textRenderer, number+"/2", 750, 400, -0, false);
            String string = number+"  /  2";
            context.drawTextWrapped(((InGameHudAccessor) self).clientAccessor().textRenderer, Text.literal(string),800,400,200,0);
        }
    }
}

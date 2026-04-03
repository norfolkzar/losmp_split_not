package net.shadow.losmp.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
@Mixin(ServerPlayerEntity.class)
public interface  ServerPlayerEntityAccessor {
    @Invoker("acceptsChatMessage")
    boolean acceptChatMessagesInvoker();
}

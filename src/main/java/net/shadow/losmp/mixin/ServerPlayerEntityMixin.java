package net.shadow.losmp.mixin;

import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    /**
     * @author Itachi(norfolkzar on discord)
     * @reason to make it work duh
     */
    @Overwrite
    public void sendChatMessage(SentMessage message, boolean filterMaskEnabled, MessageType.Parameters params){
        ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;
        if(self.hasPermissionLevel(2) && ((ServerPlayerEntityAccessor) self).acceptChatMessagesInvoker()){
            message.send(self,filterMaskEnabled,params);
        }
    }
}

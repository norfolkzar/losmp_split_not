package net.shadow.losmp.events;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChatEvent implements ServerMessageEvents.ChatMessage {
    @Override
    public void onChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) {
        if(sender.hasPermissionLevel(2)){
            MinecraftServer server = sender.getServer();
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if(server.getPlayerManager().isOperator(player.getGameProfile())){
                    continue;
                }
                player.sendMessage(message.getContent(), false);
            }
        }
    }
}

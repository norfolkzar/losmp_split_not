package net.shadow.losmp.registries;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.shadow.losmp.command.DebuffsCommand;

public class CommandRegistry {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)->{
            DebuffsCommand.register(dispatcher,false);
        });
    }
}

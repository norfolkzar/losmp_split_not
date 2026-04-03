package net.shadow.losmp.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;

public class ModScreenHandlers {
    public static final ScreenHandlerType<EskyBlockScreenHandler> ESKY_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Losmp.MOD_ID, "esky_block_screen_handler"),
                    new ExtendedScreenHandlerType<>(EskyBlockScreenHandler::new));

    public static final ScreenHandlerType<RadioScreenHandler> RADIO_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Losmp.MOD_ID, "radio_block_screen_handler"),
                    new ExtendedScreenHandlerType<>(RadioScreenHandler::new));


    public static void registerScreenHandlers() {
        Losmp.LOGGER.info("Registering Screen Handlers for " + Losmp.MOD_ID);
    }
}

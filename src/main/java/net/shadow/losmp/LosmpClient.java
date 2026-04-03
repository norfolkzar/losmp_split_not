package net.shadow.losmp;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.shadow.losmp.screen.EskyBlockScreen;
import net.shadow.losmp.screen.ModScreenHandlers;
import net.shadow.losmp.screen.RadioBlockScreen;

public class LosmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.ESKY_BLOCK_SCREEN_HANDLER, EskyBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.RADIO_BLOCK_SCREEN_HANDLER, RadioBlockScreen::new);

    }
}

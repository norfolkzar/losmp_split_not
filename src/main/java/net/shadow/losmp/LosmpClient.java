package net.shadow.losmp;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.shadow.losmp.screen.EskyBlockScreen;
import net.shadow.losmp.registries.ModScreenHandlers;
import net.shadow.losmp.screen.RadioBlockScreen;
import net.shadow.losmp.screen.SignalBoosterScreen;

public class LosmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.ESKY_BLOCK_SCREEN_HANDLER, EskyBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.RADIO_BLOCK_SCREEN_HANDLER, RadioBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.SIGNAL_BOOSTER_BLOCK_SCREEN_HANDLER, SignalBoosterScreen::new);
    }
}

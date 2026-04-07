package net.shadow.losmp;

import io.github.lounode.eventwrapper.fabric.AutoEventSubscriberRegistryFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.shadow.losmp.events.PlayerKilledByPlayerHandler;
import net.shadow.losmp.registries.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Losmp implements ModInitializer {
	public static final String MOD_ID = "losmp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModConfigs.init();
        ModEffects.registerEffects();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModSounds.registerSounds();
        ModBlockEntities.registerModBlockEntities();
        ModScreenHandlers.registerScreenHandlers();
        CommandRegistry.registerCommands();
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(mod -> {
            if (mod instanceof ModContainerImpl impl) {
                AutoEventSubscriberRegistryFabric.register(impl);
            }
        });
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new PlayerKilledByPlayerHandler());
		LOGGER.info("Hello Fabric world!");
	}
}
package net.shadow.losmp.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.shadow.losmp.config.ModConfigs;
import net.shadow.losmp.registries.ModEffects;

public class PlayerKilledByPlayerHandler implements ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if(entity instanceof ServerPlayerEntity killingPlayer && killedEntity instanceof ServerPlayerEntity killedPlayer && !killedPlayer.getCommandTags().contains("monster") && !killingPlayer.getCommandTags().contains("monster") &&!killedEntity.hasStatusEffect(ModEffects.MIMIC_EFFECT) && ModConfigs.isKillingPlayerEnabled.equals(true) && !world.isClient) {
            var amplifier = killingPlayer.getStatusEffect(ModEffects.BLOODSOAKED).getAmplifier();
            if (killingPlayer.hasStatusEffect(ModEffects.BLOODSOAKED)) {
                killingPlayer.addStatusEffect(new StatusEffectInstance(ModEffects.BLOODSOAKED, Integer.MAX_VALUE, amplifier + 1));
            } else
                killingPlayer.addStatusEffect(new StatusEffectInstance(ModEffects.BLOODSOAKED,Integer.MAX_VALUE));
        }
    }
}

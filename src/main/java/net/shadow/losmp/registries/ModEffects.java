package net.shadow.losmp.registries;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;
import net.shadow.losmp.effect.BloodSoaked;
import net.shadow.losmp.effect.MimicEffect;
import net.shadow.losmp.effect.ScreenShakeEffect;

public class ModEffects {
    public static StatusEffect BLOODSOAKED;
    public static StatusEffect MIMIC_EFFECT;
    public static StatusEffect SCREEN_SHAKE_EFFECT;

    public static StatusEffect registerBloodSoakedMethod(String name){
        return Registry.register(Registries.STATUS_EFFECT,new Identifier(Losmp.MOD_ID,name),
                new BloodSoaked(StatusEffectCategory.HARMFUL,0x880808));
    }
    public static StatusEffect registerMimicEffectMethod(String name){
        return Registry.register(Registries.STATUS_EFFECT,new Identifier(Losmp.MOD_ID,name),
                new MimicEffect(StatusEffectCategory.HARMFUL,0x880808));
    }
    public static StatusEffect registerScreenShakeEffectMethod(String name){
        return Registry.register(Registries.STATUS_EFFECT,new Identifier(Losmp.MOD_ID,name),
                new ScreenShakeEffect(StatusEffectCategory.HARMFUL,0x880808));
    }

    public static void registerEffects(){
        Losmp.LOGGER.info("Registering Effects for " + Losmp.MOD_ID);
        SCREEN_SHAKE_EFFECT = registerScreenShakeEffectMethod("screen_shake_effect");
        BLOODSOAKED = registerBloodSoakedMethod("blood_soaked");
        MIMIC_EFFECT = registerMimicEffectMethod("mimic_effect");
    }
}

package net.shadow.losmp.config;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModConfigs {
    public static GameRules.Key<GameRules.BooleanRule> allowTaskBlockFails;
    public static GameRules.Key<GameRules.BooleanRule> isDrowningOn ;
    public static GameRules.Key<GameRules.BooleanRule> isOilrigSoundOn ;
    public static GameRules.Key<GameRules.BooleanRule> isKillingPlayerEnabled ;
    public static GameRules.Key<GameRules.BooleanRule> isEngineWorking ;
    public static GameRules.Key<GameRules.BooleanRule> isGyroZeppeliWorking ;
    public static GameRules.Key<GameRules.BooleanRule> isFlairWorking ;
    public static GameRules.Key<GameRules.BooleanRule> isRadioWorking ;

    public static void init(){
        allowTaskBlockFails = GameRuleRegistry.register("allowTaskBlockFails", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
        isDrowningOn = GameRuleRegistry.register("isDrowningEnabled", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
        isOilrigSoundOn = GameRuleRegistry.register("isOilrigSoundsEnabled", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
        isKillingPlayerEnabled = GameRuleRegistry.register("isKillingDebuffsEnabled", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
        isEngineWorking = GameRuleRegistry.register("isEngineWorking", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
        isGyroZeppeliWorking = GameRuleRegistry.register("isGyroWorking", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
        isFlairWorking = GameRuleRegistry.register("isFlairWorking", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
        isRadioWorking = GameRuleRegistry.register("isRadioWorking", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
    }
}

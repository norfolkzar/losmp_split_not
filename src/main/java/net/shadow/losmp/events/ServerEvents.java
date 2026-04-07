package net.shadow.losmp.events;

import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;
import io.github.lounode.eventwrapper.event.server.ServerStartingEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import net.shadow.losmp.registries.ModConfigs;
import net.shadow.losmp.registries.ModSounds;

import java.util.Random;

//livingEntity.hasPermissionLevel() to check if the player has OP or smthing
@EventBusSubscriberWrapper
public class ServerEvents {

    static Random random = new Random();
    private static double scarySoundCooldownForY0 = 20 * 60 * 3 + random.nextInt(10) * 20 * 6;
    private static double scarySoundCooldownForY80 = 20 * 60 * 3 + random.nextInt(10) * 20 * 6;
    private static double scarySoundCooldownForY115 = 20 * 60 * 3 + random.nextInt(10) * 20 * 6;
    private static double flairBreakWait = 0;
    private static double globalCoolDown = 0;
    private static double firstTimeTimer = 0;
    public static String awooga ="awooga";
    public static Boolean canShitBreak = false;
    public static Boolean firstTime = false;
    public static Boolean flairWorking = true;
    @SubscribeEventWrapper
    public static void OnLivingEntityTick(LivingEventWrapper.LivingTickEvent event) {
        var livingEntity = event.getEntity();
        var level = livingEntity.getWorld();
        var isDrowningOn = level.getServer().getGameRules().getBoolean(ModConfigs.isDrowningOn);
        var isOilrigSoundOn = level.getServer().getGameRules().getBoolean(ModConfigs.isOilrigSoundOn);
        var allowTaskBlockFails = level.getServer().getGameRules().get(ModConfigs.allowTaskBlockFails);
        var isEngineWorking = level.getServer().getGameRules().get(ModConfigs.isEngineWorking);
        var isFlairWorking = level.getServer().getGameRules().get(ModConfigs.isFlairWorking);
        var isRadioWorking = level.getServer().getGameRules().get(ModConfigs.isRadioWorking);
        var isGyroZeppeliWorking = level.getServer().getGameRules().get(ModConfigs.isGyroZeppeliWorking);

        if(isOilrigSoundOn) {
            if (scarySoundCooldownForY0 > 0 && livingEntity.getY() <= 0) {
                scarySoundCooldownForY0--;
            }
            if (scarySoundCooldownForY80 > 0 && livingEntity.getY() <= 80 && livingEntity.getY() > 0) {
                scarySoundCooldownForY80--;
            }
            if (scarySoundCooldownForY115 > 0 && livingEntity.getY() >= 115) {
                scarySoundCooldownForY115--;
            }
            if (livingEntity.getY() <= 0 && level.isClient() && scarySoundCooldownForY0 == 0 && livingEntity.isSubmergedInWater()) {
                level.playSound(null, livingEntity.getBlockPos(), deepSeaSounds(), SoundCategory.AMBIENT, 1f, 1f);
                scarySoundCooldownForY0 = 20 * 60 * 3 + random.nextInt(10) * 20 * 6;
            }
            if (livingEntity.getY() <= 80 && livingEntity.getY() > 0 && level.isClient() && scarySoundCooldownForY80 == 0 && livingEntity.isSubmergedInWater()) {
                level.playSound(null, livingEntity.getBlockPos(), placeHolderSoundfory80(), SoundCategory.AMBIENT, 1f, 1f);
                scarySoundCooldownForY80 = 20 * 60 * 3 + random.nextInt(10) * 20 * 6;
            }
            if (livingEntity.getY() >= 115 && level.isClient() && scarySoundCooldownForY115 == 0) {
                level.playSound(null, livingEntity.getBlockPos(), placeHolderSoundfory115(), SoundCategory.AMBIENT, 1f, 1f);
                scarySoundCooldownForY115 = 20 * 60 * 3 + random.nextInt(10) * 20 * 6;
            }
        }

        if ((livingEntity instanceof PlayerEntity player) && livingEntity.isTouchingWater() && !player.isCreative() && isDrowningOn) {
            livingEntity.addVelocity(0,-0.045,0);
            if (player.isInSwimmingPose()) {
                player.addVelocity(0,-0.08,0);
            }
            if (player.getAir() <= 20 && player.isSubmergedInWater() && player.getAir() > 15) {
                level.playSound(null, livingEntity.getBlockPos(), placeHolderSoundfordrowning_subtle(), SoundCategory.AMBIENT, 1f, 1f);
            }
            if (player.getAir() <= 15 && player.getAir() > 10 && player.isSubmergedInWater()) {
                level.playSound(null, livingEntity.getBlockPos(), placeHolderSoundfordrowning_quiet(), SoundCategory.AMBIENT, 1f, 1f);
            }
            if (player.getAir() <= 10 && player.getAir() > 5 && player.isSubmergedInWater()) {
                level.playSound(null, livingEntity.getBlockPos(), placeHolderSoundfordrowning(), SoundCategory.AMBIENT, 1f, 1f);
            }
            if (player.getAir() <= 5) {
                level.playSound(null, livingEntity.getBlockPos(), placeHolderSoundfordrowning_loud(), SoundCategory.AMBIENT, 1f, 1f);
            }
        }
        if(allowTaskBlockFails.get()) {
            if (!firstTime) {
                if (awooga.equals("yes")) {
                    globalCoolDown++;
                }
                if (awooga.equals("yes") && globalCoolDown >= getBoundedRandom(20 * 60 * 10, 20 * 60 * 30)) {
                    awooga = "no";
                    globalCoolDown = 0;
                    canShitBreak = true;
                }
                if(!flairWorking){
                    flairBreakWait++;
                    if(flairBreakWait>=20*60*20){
                        isFlairWorking.set(false,level.getServer());
                    }
                }
                if (canShitBreak) {
                    canShitBreak = false;
                    var random = Math.random();
                    if (random > 0.75) {
                        flairWorking= false;
                    } else if (random > 0.5) {
                        isRadioWorking.set(false, level.getServer());
                    } else if (random > 0.25) {
                        isGyroZeppeliWorking.set(false, level.getServer());
                    } else {
                        isEngineWorking.set(false, level.getServer());
                    }
                }
            } else {
                firstTimeTimer++;
                if(firstTimeTimer>= getBoundedRandom(20*60*5,20*60*10)){
                    var random = Math.random();
                    if (random > 0.75) {
                        isFlairWorking.set(false, level.getServer());
                    } else if (random > 0.5) {
                        isRadioWorking.set(false, level.getServer());
                    } else if (random > 0.25) {
                        isGyroZeppeliWorking.set(false, level.getServer());
                    } else {
                        isEngineWorking.set(false, level.getServer());
                    }
                    firstTime=false;
                    firstTimeTimer = 0;
                }
            }
        }
    }

    @SubscribeEventWrapper
    public static void onServerStart(ServerStartingEventWrapper event){
        var server = event.getServer();
        server.getGameRules().get(GameRules.REDUCED_DEBUG_INFO).set(true,server);
        server.getGameRules().get(GameRules.ANNOUNCE_ADVANCEMENTS).set(true,server);
    }

    private static int getBoundedRandom(int min,int max) {
        return min + (int)(Math.random() * (max - min));
    }

    public static SoundEvent deepSeaSounds() {
        var random = Math.random();
        if(random>=0.5){
            return ModSounds.deepSeaSounds1;
        }
        else return ModSounds.deepSeaSounds2;
    }
    public static SoundEvent placeHolderSoundfory80() {
        var random = Math.random();
        if(random>=0.5){
            return ModSounds.seaSounds1;
        }
        else return ModSounds.seaSounds2;
    }
    public static SoundEvent placeHolderSoundfory115() {
        var random = Math.random();
        if(random>0.75){
            return ModSounds.OilRigCreakFour;
        }
        else if(random>0.5){
            return ModSounds.OilRigCreakThree;

        }
        else if(random>0.25){
            return ModSounds.OilRigCreakTwo;

        }
        else {
            return ModSounds.OilRigCreakOne;
        }
    }
    public static SoundEvent placeHolderSoundfordrowning() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }
    public static SoundEvent placeHolderSoundfordrowning_subtle() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }
    public static SoundEvent placeHolderSoundfordrowning_quiet() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }
    public static SoundEvent placeHolderSoundfordrowning_loud() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }
    public static void isAnythingFixed(String string){
        awooga =string;
    }
}

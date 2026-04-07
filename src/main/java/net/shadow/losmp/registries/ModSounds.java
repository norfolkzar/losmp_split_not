package net.shadow.losmp.registries;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;

public class ModSounds {
    public static final SoundEvent deepSeaSounds1 = registerSoundEvent("deep_sea_sounds_1");
    public static final SoundEvent deepSeaSounds2 = registerSoundEvent("deep_sea_sounds_2");
    public static final SoundEvent seaSounds1 = registerSoundEvent("sea_sounds_1");
    public static final SoundEvent seaSounds2 = registerSoundEvent("sea_sounds_2");
    public static final SoundEvent engineWorkingSound = registerSoundEvent("engine_working");
    public static final SoundEvent engineBrokenSound = registerSoundEvent("engine_broken");
    public static final SoundEvent OilRigCreakFour = registerSoundEvent("oil_rig_creak_four");
    public static final SoundEvent OilRigCreakThree = registerSoundEvent("oil_rig_creak_three");
    public static final SoundEvent OilRigCreakTwo = registerSoundEvent("oil_rig_creak_two");
    public static final SoundEvent OilRigCreakOne = registerSoundEvent("oil_rig_creak_one");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(Losmp.MOD_ID,name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }

    public static void registerSounds(){
        Losmp.LOGGER.info("Registering sounds");
    }
}

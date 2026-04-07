package net.shadow.losmp.registries;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;
import net.shadow.losmp.item.custom.FuelCanisterItem;
import net.shadow.losmp.item.custom.ShotgunAmmoItem;
import net.shadow.losmp.item.custom.ShotgunItem;
import net.shadow.losmp.item.custom.SignalDrive;

public class ModItems {

    public static final Item SHOTGUN = registerItems("shotgun",
            new ShotgunItem(new Item.Settings().maxCount(1)));

    public static final Item SHOTGUN_AMMO = registerItems("shotgun_shell",
            new ShotgunAmmoItem(new Item.Settings()));

    public static final Item WRENCH_ITEM = registerItems("wrench",
            new Item(new Item.Settings()));

    public static final Item RADIO_PART_ITEM = registerItems("radio_part",
            new Item(new Item.Settings()));

    public static final Item SIGNAL_DRIVE_ITEM = registerItems("signal_drive_offline",
            new SignalDrive(new Item.Settings().maxCount(1)));

    public static final Item FUEL_CANISTER_ITEM = registerItems("fuel_canister",
            new FuelCanisterItem(new Item.Settings().maxCount(1)));


    private static void addItemsToCreativeTab(FabricItemGroupEntries entries){
        entries.add(WRENCH_ITEM);
    }

    private static Item registerItems(String name, Item item){
        return Registry.register(Registries.ITEM,new Identifier(Losmp.MOD_ID,name),item);
    }


    public static void registerModItems(){
        Losmp.LOGGER.info("Registering Items for " + Losmp.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToCreativeTab);
    }
}

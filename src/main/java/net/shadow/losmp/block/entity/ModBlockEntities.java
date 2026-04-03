package net.shadow.losmp.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;
import net.shadow.losmp.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<EskyBlockEntity> ESKY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Losmp.MOD_ID,"esky_block_entity"),
                    FabricBlockEntityTypeBuilder.create(EskyBlockEntity::new, ModBlocks.ESKY_BLOCK).build());

    public static final BlockEntityType<RadioBlockEntity> RADIO_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Losmp.MOD_ID,"radio_block_entity"),
                    FabricBlockEntityTypeBuilder.create(RadioBlockEntity::new, ModBlocks.RADIO_BLOCK).build());

    public static final BlockEntityType<FlairBlockEntity> FLAIR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Losmp.MOD_ID, "flair_block_entity"),
                    FabricBlockEntityTypeBuilder.create(FlairBlockEntity::new, ModBlocks.FLAIR_BLOCK).build());

    public static void registerModBlockEntities(){
        Losmp.LOGGER.info("Registering Block Entities for " + Losmp.MOD_ID);
    }
}
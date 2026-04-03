package net.shadow.losmp.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;

public class ModBlocks {

    public static final Block ENGINE_BLOCK = registerBlock("engine",
            new EngineBlock(FabricBlockSettings.create()));

    public static final Block ESKY_BLOCK = registerBlock("esky",
            new EskyBlock(FabricBlockSettings.create()));

    public static final Block RADIO_BLOCK = registerBlock("radio",
            new RadioBlock(FabricBlockSettings.create()));


    public static final Block LIGHT_BLOCK = registerBlock("light_block",
                    new FlickerLightBlock(AbstractBlock.Settings.create()
                            .luminance(state -> {
                                int f = state.get(FlickerLightBlock.FLICKER);
                                if (f == 0) return 15;
                                if (f == 1) return 10;
                                return 6;
                            })
                    )
            );

    public static final Block FLAIR_BLOCK = registerBlock("flair_block",
            new FlairBlock(AbstractBlock.Settings.create()));


    private static Block registerBlock(String name , Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(Losmp.MOD_ID,name),
                block);
    }


    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM,new Identifier(Losmp.MOD_ID,name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks(){
        Losmp.LOGGER.info("Registering Blocks for " + Losmp.MOD_ID);
    }
}

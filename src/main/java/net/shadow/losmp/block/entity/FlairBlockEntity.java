package net.shadow.losmp.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadow.losmp.config.ModConfigs;

public class FlairBlockEntity extends BlockEntity {
    public FlairBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLAIR_BLOCK_ENTITY, pos, state);
    }

    private int timer = 0;

    public static void tick(World world, BlockPos pos, BlockState state, FlairBlockEntity be) {
        if (!world.isClient && ModConfigs.isFlairWorking.equals(false) && !(be.timer >= 1200)) {
            be.timer++;
            if (be.timer >= 1200) {
                be.timer = 0;
                for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers()) {
                    player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), SoundCategory.BLOCKS, 1f, 1f);
                }
            }
        }
        if(!world.isClient && ModConfigs.isFlairWorking.equals(true)){
            be.timer =0;
        }
    }

}

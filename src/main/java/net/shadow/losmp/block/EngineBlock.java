package net.shadow.losmp.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.shadow.losmp.registries.ModConfigs;
import net.shadow.losmp.registries.ModSounds;
import org.jetbrains.annotations.Nullable;

public class EngineBlock extends HorizontalFacingBlock {

    public EngineBlock(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.scheduleBlockTick(pos,state.getBlock(),1);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var block = world.getBlockState(pos);
        var server = world.getServer();
        var rule = server.getGameRules().getBoolean(ModConfigs.isEngineWorking);
        if (rule) {
            world.playSound((double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F, ModSounds.engineWorkingSound, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            world.scheduleBlockTick(pos,block.getBlock(),25);
        }
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        var server = world.getServer();
        var rule = server.getGameRules().getBoolean(ModConfigs.isEngineWorking);
        if (!rule && random.nextInt(10) == 0) {
            world.playSound((double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F, ModSounds.engineBrokenSound, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
        }
    }
}


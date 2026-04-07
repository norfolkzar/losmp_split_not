package net.shadow.losmp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.shadow.losmp.registries.ModConfigs;
import org.jetbrains.annotations.Nullable;

public class FlickerLightBlock extends Block {

    public static final IntProperty FLICKER = IntProperty.of("flicker", 0, 2);

    public FlickerLightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FLICKER, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLICKER);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient){
            world.scheduleBlockTick(pos, this, 5);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var rule = world.getServer().getGameRules().getBoolean(ModConfigs.isEngineWorking);
        if (!rule) {
            int flicker = random.nextInt(3);
            world.setBlockState(pos, state.with(FLICKER, flicker));
            world.scheduleBlockTick(pos, this, 5);
        }
    }


}
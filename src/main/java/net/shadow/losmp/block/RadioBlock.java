package net.shadow.losmp.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadow.losmp.block.entity.ModBlockEntities;
import net.shadow.losmp.block.entity.RadioBlockEntity;
import org.jetbrains.annotations.Nullable;

public class RadioBlock extends BlockWithEntity {
    public RadioBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RadioBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var blockEntity = world.getBlockEntity(pos);
        if (!world.isClient) {
            if (blockEntity instanceof RadioBlockEntity radioBlockEntity && radioBlockEntity.waitTime >= 100 && radioBlockEntity.startTimer) {
                radioBlockEntity.waitTime = 0;
                radioBlockEntity.startTimer = false;
                radioBlockEntity.numberOfSignals++;
                world.playSound(null,pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS,1f,1f);
                for(PlayerEntity playerEntity : world.getPlayers()){
                    if(player.hasPermissionLevel(2)){
                        playerEntity.sendMessage(Text.literal("Radio Signals sent:" + radioBlockEntity.numberOfSignals));
                    }
                }
            } else {
                NamedScreenHandlerFactory screenHandlerFactory = ((RadioBlockEntity) world.getBlockEntity(pos));
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.RADIO_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RadioBlockEntity(pos,state);
    }
}

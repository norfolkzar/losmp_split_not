package net.shadow.losmp.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.shadow.losmp.registries.ModBlockEntities;
import net.shadow.losmp.block.entity.RadioBlockEntity;
import net.shadow.losmp.registries.ModConfigs;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignalDrive extends Item {
    public SignalDrive(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var player = context.getPlayer();
        var level = context.getWorld();
        var server = level.getServer();
        var pos = context.getBlockPos();
        var block = level.getBlockEntity(pos);
        var itemStack = context.getStack();
        var rule = server.getGameRules().get(ModConfigs.isEngineWorking);
        if(block.getType() == ModBlockEntities.RADIO_BLOCK_ENTITY && itemStack.getOrCreateNbt().getInt("losmp.signal_power")==1 && rule.get()){
            rule.set(false,server);
            itemStack.getOrCreateNbt().putInt("losmp.signal_power",0);
            if (!level.isClient) {
                if (block instanceof RadioBlockEntity radioBlockEntity && radioBlockEntity.waitTime >= 100 && radioBlockEntity.startTimer) {
                    radioBlockEntity.waitTime = 0;
                    radioBlockEntity.startTimer = false;
                    radioBlockEntity.numberOfSignals++;
                    level.playSound(null,pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS,1f,1f);
                    for(PlayerEntity playerEntity : level.getPlayers()){
                        if(player.hasPermissionLevel(2)){
                            playerEntity.sendMessage(Text.literal("Radio Signals sent:" + radioBlockEntity.numberOfSignals));
                        }
                    }
                } else {
                    NamedScreenHandlerFactory screenHandlerFactory = ((RadioBlockEntity) level.getBlockEntity(pos));
                    if (screenHandlerFactory != null) {
                        player.openHandledScreen(screenHandlerFactory);
                    }
                }
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt() || !stack.getNbt().contains("losmp.signal_power")) {
            stack.getOrCreateNbt().putInt("losmp.signal_power", 0);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String ammo = "Current charge is " + stack.getNbt().getInt("losmp.signal_power")*100+"%";
        tooltip.add(Text.literal(ammo));
    }
}

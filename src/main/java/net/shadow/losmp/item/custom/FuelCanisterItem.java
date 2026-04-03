package net.shadow.losmp.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.shadow.losmp.block.ModBlocks;
import net.shadow.losmp.config.ModConfigs;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuelCanisterItem extends Item {
    public FuelCanisterItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var level = context.getWorld();
        var player = context.getPlayer();
        var server = player.getServer();
        var block = level.getBlockState(context.getBlockPos());
        var itemStack = player.getMainHandStack();
        if(block.hasBlockEntity() && block.getBlock() == ModBlocks.FLAIR_BLOCK && ModConfigs.isFlairWorking.equals(false) && itemStack.getNbt().getInt("losmp.fuel") == 1){
            itemStack.getNbt().putInt("losmp.fuel",0);
            var rule = server.getGameRules().get(ModConfigs.isFlairWorking);
            rule.set(true,server);
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String ammo = "Current fuel is " + stack.getNbt().getInt("losmp.fuel")*100+"%";
        tooltip.add(Text.literal(ammo));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt() || !stack.getNbt().contains("losmp.fuel")) {
            stack.getOrCreateNbt().putInt("losmp.fuel", 1);
        }
    }
}

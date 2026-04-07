package net.shadow.losmp.item.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.shadow.losmp.registries.ModItems;
import net.shadow.losmp.item.client.ShotgunRenderer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import javax.sql.rowset.CachedRowSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ShotgunItem extends Item implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public ShotgunItem(Settings settings) {
        super(settings);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
            if (!(itemStack.getNbt().getInt("losmp.ammo")== 0)) {
                EntityHitResult result = hitScanEntities(player, 15);
                if (itemStack.getNbt().getInt("losmp.ammo") > 0 && !world.isClient) {
                    player.getItemCooldownManager().set(this, 100);
                    //triggerAnim(player,GeoItem.getOrAssignId(player.getStackInHand(hand), (ServerWorld) world), "shoot_controller", "shoot");
                    //MinecraftClient.getInstance().setScreen();
                    world.playSound(null,player.getBlockPos(),SoundEvents.BLOCK_BEACON_AMBIENT,SoundCategory.PLAYERS,1f,1f);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,20*3,1));
                    player.getStackInHand(hand);
                    world.playSound(null,player.getBlockPos(),SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE,SoundCategory.AMBIENT,10f,10f);
                    itemStack.getOrCreateNbt().putInt("losmp.ammo", itemStack.getNbt().getInt("losmp.ammo") - 1);
                    Entity hit = result.getEntity();
                    hit.damage(player.getDamageSources().playerAttack(player), 100.0F);
                    return TypedActionResult.success(itemStack, world.isClient());
                }
                return TypedActionResult.success(itemStack, world.isClient());
            }
            else {
                itemStack.getOrCreateNbt().putBoolean("losmp.reloading", true);
                if(!world.isClient) {
                    //triggerAnim(player, GeoItem.getOrAssignId(player.getStackInHand(hand), (ServerWorld) world), "reload_controller", "reload");
                }
                player.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            }
    }


    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 140;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(remainingUseTicks>0){
            stack.getOrCreateNbt().putBoolean("losmp.reloading", false);
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity user) {
        if ((user instanceof PlayerEntity player)) {
            if (reloadForPlayer(player)) {
                itemStack.getOrCreateNbt().putBoolean("losmp.reloading", false);
                itemStack.getNbt().putInt("losmp.ammo", 1);
                if (partialReload(player)) {
                    itemStack.getNbt().putInt("losmp.ammo", 2);
                }
                if (!world.isClient) {
                    world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundCategory.PLAYERS, 1f, 1f);
                    player.getItemCooldownManager().set(this, 20);
                }
            }
        }
        return super.finishUsing(itemStack, world, user);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt() || !stack.getNbt().contains("losmp.ammo")) {
            stack.getOrCreateNbt().putBoolean("losmp.reloading", false);
            stack.getOrCreateNbt().putInt("losmp.ammo", 2);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String ammo = "Current ammo is " + stack.getNbt().getInt("losmp.ammo");
        tooltip.add(Text.literal(ammo));
    }

    public static EntityHitResult hitScanEntities(PlayerEntity player, double range) {
        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d direction = player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(range));
        Box searchBox = player.getBoundingBox().stretch(direction.multiply(range)).expand(1.0D);
        Predicate<Entity> filter = entity -> !entity.isSpectator() && entity.isAlive() && entity != player;
        return ProjectileUtil.raycast(player, start, end, searchBox, filter, range * range);
    }


    public static Boolean reloadForPlayer(PlayerEntity player) {
        boolean hasReloaded = false;
        for(int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (!itemStack.isEmpty() && itemStack.isOf(ModItems.SHOTGUN_AMMO)){
                itemStack.decrement(1);
                hasReloaded = true;
                break;
            }
        }
        return hasReloaded;
    }

    public static Boolean partialReload(PlayerEntity player) {
        boolean secondshell = false;
        for(int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (!itemStack.isEmpty() && itemStack.isOf(ModItems.SHOTGUN_AMMO)){
                itemStack.decrement(2);
                secondshell = true;
                break;
            }
        }
        return secondshell;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final ShotgunRenderer renderer = new ShotgunRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "shoot_controller", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("shoot", Animation.LoopType.DEFAULT)));
        controllerRegistrar.add(new AnimationController<>(this, "reload_controller", state -> PlayState.STOP)
                .triggerableAnim("reload", RawAnimation.begin().then("reload", Animation.LoopType.DEFAULT)));
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}

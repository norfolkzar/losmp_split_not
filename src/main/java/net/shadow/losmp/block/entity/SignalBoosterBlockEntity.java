package net.shadow.losmp.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shadow.losmp.registries.ModBlockEntities;
import net.shadow.losmp.screen.SignalBoosterScreenHandler;
import org.jetbrains.annotations.Nullable;

public class SignalBoosterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4,ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;

    public SignalBoosterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIGNAL_BOOSTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.losmp.signal_booster");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SignalBoosterScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {

    }
}

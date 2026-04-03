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
import net.shadow.losmp.config.ModConfigs;
import net.shadow.losmp.item.ModItems;
import net.shadow.losmp.screen.RadioScreenHandler;
import org.jetbrains.annotations.Nullable;

public class RadioBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3,ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    public int numberOfSignals = 0;
    public boolean startTimer = false;
    public int waitTime = 0;

    public RadioBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIO_BLOCK_ENTITY, pos, state);
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
                return 27;
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
        return Text.literal("Radio Block");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RadioScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        var blockEntity = world.getBlockEntity(pos);
        if(!world.isClient() && blockEntity instanceof RadioBlockEntity radioBlockEntity && ModConfigs.isRadioWorking.equals(false)){
            if(startTimer){
                waitTime++;
            }
            var radioParts = new ItemStack(ModItems.RADIO_PART_ITEM);
            if(radioBlockEntity.inventory.get(0).equals(radioParts)&& radioBlockEntity.inventory.get(1).equals(radioParts)&&radioBlockEntity.inventory.get(2).equals(radioParts)){
                startTimer = true;
                radioBlockEntity.inventory.set(0,ItemStack.EMPTY);
                radioBlockEntity.inventory.set(1,ItemStack.EMPTY);
                radioBlockEntity.inventory.set(2,ItemStack.EMPTY);
            }
        }
    }
}

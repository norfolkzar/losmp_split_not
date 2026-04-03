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
import net.shadow.losmp.screen.EskyBlockScreenHandler;
import org.jetbrains.annotations.Nullable;

public class EskyBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27,ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;

    public EskyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESKY_BLOCK_ENTITY, pos, state);
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
        return Text.literal("Esky Block");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EskyBlockScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(!world.isClient){
            var blockEntity = world.getBlockEntity(pos);
            if(blockEntity.getType() == ModBlockEntities.ESKY_BLOCK_ENTITY){
                for(int i=0; i <27; i++){
                    var itemStack = ((EskyBlockEntity) blockEntity).inventory.get(i);
                    if(itemStack.isFood()){
                        var nbt = itemStack.getOrCreateNbt();
                        int age = nbt.getInt("losmp.food_decay");
                        if(age>0){
                            age = age - 2;
                            nbt.putInt("losmp.food_decay", age);
                        }
                    }
                }
            }
        }
    }
}
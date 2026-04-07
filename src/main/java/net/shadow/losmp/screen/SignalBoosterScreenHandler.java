package net.shadow.losmp.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.shadow.losmp.Losmp;
import net.shadow.losmp.block.entity.SignalBoosterBlockEntity;
import net.shadow.losmp.registries.ModBlockEntities;
import net.shadow.losmp.registries.ModScreenHandlers;

import static net.shadow.losmp.registries.ModItems.SIGNAL_DRIVE_ITEM;

public class SignalBoosterScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final SignalBoosterBlockEntity blockEntity;
    public SignalBoosterScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf){
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(27));
    }


    public SignalBoosterScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.SIGNAL_BOOSTER_BLOCK_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),4);
        this.inventory = ((Inventory) blockEntity);
        playerInventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (SignalBoosterBlockEntity) blockEntity;
        for(int k = 0; k < 3; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k *75, 18));
        }
        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addProperties(arrayPropertyDelegate);
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack() && invSlot !=4) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        for (int i = 0; i < 3; i++) {
            var itemSlot = this.inventory.getStack(i);
            if (!itemSlot.isEmpty() && itemSlot.getItem() == SIGNAL_DRIVE_ITEM) {
                itemSlot.getOrCreateNbt().putInt("losmp.signal_power", 1);
            }
        }
        return true;
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}

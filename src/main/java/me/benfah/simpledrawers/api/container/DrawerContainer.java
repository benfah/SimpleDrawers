package me.benfah.simpledrawers.api.container;

import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import java.util.HashSet;
import java.util.Set;

import static me.benfah.simpledrawers.init.SDContainers.*;

public class DrawerContainer extends ScreenHandler
{

    public PlayerInventory playerInv;

    public Set<HolderSlot> holderSlots = new HashSet<>();

    public DrawerContainer(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, BlockEntityAbstractDrawer drawer)
    {

        super(type, syncId);
        this.playerInv = inventory;
//		HolderSlot slot = new HolderSlot(() -> drawer.getItemHolders().get(0), 0, 80, 44);
//		holderSlots.add(slot);
//		addSlot(slot);

        // this is not ideal but there doesn't seem to be a good way to deal with ScreenHandlerType both here
        // and in the ExtendedScreenHandlerFactory inside BlockAbstractDrawer at the same time
        if (type.equals(BASIC_DRAWER_CONTAINER_TYPE)) {
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(0), 80, 44));
        } else if (type.equals(DOUBLE_DRAWER_CONTAINER_TYPE)) {
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(0), 80, 35));
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(1), 80, 53));
        } else if (type.equals(QUAD_DRAWER_CONTAINER_TYPE)) {
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(0), 71, 35));
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(1), 89, 35));
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(2), 71, 53));
            holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(3), 89, 53));
        }

        int m;
        for(m = 0; m < 3; ++m) {
           for(int l = 0; l < 9; ++l) {
              this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 105 + m * 18));
           }
        }

        for(m = 0; m < 9; ++m) {
           this.addSlot(new Slot(inventory, m, 8 + m * 18, 163));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return true;
    }
    
    @Override
    public ItemStack transferSlot(PlayerEntity player, int index)
    {
    	return ItemStack.EMPTY;
    }
    
}

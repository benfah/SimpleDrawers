package me.benfah.simpledrawers.api.container;

import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.HashSet;
import java.util.Set;

public class DrawerContainer<T extends BlockEntityAbstractDrawer> extends ScreenHandler
{

    public PlayerInventory playerInv;

    public Set<HolderSlot> holderSlots = new HashSet<>();

    public T drawer;


    public DrawerContainer(int syncId, PlayerEntity entity, T drawer)
    {

        super(null, syncId);
        this.playerInv = entity.getInventory();
        this.drawer = drawer;
//		HolderSlot slot = new HolderSlot(() -> drawer.getItemHolders().get(0), 0, 80, 44);
//		holderSlots.add(slot);
//		addSlot(slot);


        int m;
        for(m = 0; m < 3; ++m) {
           for(int l = 0; l < 9; ++l) {
              this.addSlot(new Slot(entity.getInventory(), l + m * 9 + 9, 8 + l * 18, 105 + m * 18));
           }
        }

        for(m = 0; m < 9; ++m) {
           this.addSlot(new Slot(entity.getInventory(), m, 8 + m * 18, 163));
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

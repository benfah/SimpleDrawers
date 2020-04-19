package me.benfah.simpledrawers.api.container;

import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawerContainer<T extends BlockEntityAbstractDrawer> extends ScreenHandler
{
	
	public PlayerInventory playerInv;

	public Set<HolderSlot> holderSlots = new HashSet<>();

	public T drawer;


	public DrawerContainer(int syncId, PlayerEntity entity, T drawer)
	{
		
		super(null, syncId);
		this.playerInv = entity.inventory;
		this.drawer = drawer;
//		HolderSlot slot = new HolderSlot(() -> drawer.getItemHolders().get(0), 0, 80, 44);
//		holderSlots.add(slot);
//		addSlot(slot);

		
		int k;
	      for(k = 0; k < 3; ++k) {
	         for(int j = 0; j < 9; ++j) {
	            this.addSlot(new Slot(playerInv, j + k * 9 + 9, 8 + j * 18, 105 + k * 18));
	         }
	      }

	      for(k = 0; k < 9; ++k) {
	         this.addSlot(new Slot(playerInv, k, 8 + k * 18, 163));
	      }
	}
	
	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}
	
}

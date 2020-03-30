package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.api.drawer.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import net.minecraft.entity.player.PlayerEntity;

public interface DrawerInteractable
{
	
	public void interact(BlockEntityAbstractDrawer blockEntity, PlayerEntity player, ItemHolder holder);
	
}

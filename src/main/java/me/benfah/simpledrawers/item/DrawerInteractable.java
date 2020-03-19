package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.holder.ItemHolder;
import net.minecraft.entity.player.PlayerEntity;

public interface DrawerInteractable
{
	
	public void interact(BlockEntityBasicDrawer holder, PlayerEntity player);
	
}

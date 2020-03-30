package me.benfah.simpledrawers.item;

import java.util.function.Consumer;

import me.benfah.simpledrawers.api.drawer.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class ItemKey extends Item implements DrawerInteractable
{

	private Consumer<ItemHolder> action;

	public ItemKey(Settings settings, Consumer<ItemHolder> action)
	{
		super(settings);
		this.action = action;
	}

	public void interact(BlockEntityAbstractDrawer drawer, PlayerEntity player, ItemHolder holder)
	{
		action.accept(holder);
	}

}

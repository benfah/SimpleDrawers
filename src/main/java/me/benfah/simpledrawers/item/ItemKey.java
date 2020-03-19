package me.benfah.simpledrawers.item;

import java.util.function.Consumer;

import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.holder.ItemHolder;
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

	public void interact(BlockEntityBasicDrawer drawer, PlayerEntity player)
	{
		action.accept(drawer.getHolder());
	}

}

package me.benfah.simpledrawers.item;

import java.util.function.Consumer;

import me.benfah.simpledrawers.block.entity.holder.ItemHolder;
import net.minecraft.item.Item;

public class ItemKey extends Item
{
	
	private Consumer<ItemHolder> action;
	
	public ItemKey(Settings settings, Consumer<ItemHolder> action)
	{
		super(settings);
		this.action = action;
	}
	
	public void interact(ItemHolder holder)
	{
		action.accept(holder);
	}

}

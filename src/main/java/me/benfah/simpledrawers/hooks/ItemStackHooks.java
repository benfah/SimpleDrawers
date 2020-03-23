package me.benfah.simpledrawers.hooks;

import java.util.HashMap;
import java.util.function.BiConsumer;

import net.minecraft.item.ItemStack;

public interface ItemStackHooks
{
	void addCountChangeConsumer(BiConsumer<Integer, Integer> consumer);
	
	public static void addCountChangeConsumer(ItemStack stack, BiConsumer<Integer, Integer> consumer)
	{
		CONSUMER_MAP.put(stack, consumer);
	}
	
	public static HashMap<ItemStack, BiConsumer<Integer, Integer>> CONSUMER_MAP = new HashMap<>();
	
}

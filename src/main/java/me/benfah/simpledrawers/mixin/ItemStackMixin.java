package me.benfah.simpledrawers.mixin;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import me.benfah.simpledrawers.hooks.ItemStackHooks;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
@Implements(@Interface(iface = ItemStackHooks.class, prefix = "sidr$"))
public class ItemStackMixin
{
	
	@Shadow
	int count;
	
	BiConsumer<Integer, Integer> consumer;
	
	
	public void sidr$addCountChangeConsumer(BiConsumer<Integer, Integer> consumer)
	{
		this.consumer = consumer;
	}
	
	@Inject(method = "setCount", at = @At("HEAD"))
	public void onSetCount(int newCount)
	{
		if(ItemStackHooks.CONSUMER_MAP.containsKey(this))
		{
			ItemStackHooks.CONSUMER_MAP.get(this).accept(newCount, count);
		}
	}
	
	
	
}

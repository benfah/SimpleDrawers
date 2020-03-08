package me.benfah.simpledrawers.block.entity.holder;


import java.text.NumberFormat;
import java.util.Locale;

import me.benfah.simpledrawers.utils.ItemUtils;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemHolder
{
	public static NumberFormat FORMAT = NumberFormat.getInstance(Locale.US);
	
	private ItemStack internalStack;
	private int maxAmount;
	private BlockEntityClientSerializable blockEntity;
	
	
	
	public ItemHolder(int maxAmount, BlockEntityClientSerializable blockEntity)
	{
//		this.internalStack = new ItemStack(itemType, 1);
		this.maxAmount = maxAmount;
		this.blockEntity = blockEntity;
	}
	
	private ItemHolder() {}
	
	public String getDisplayAmount()
	{
		return FORMAT.format(internalStack.getCount());
	}
	
	public ActionResult offer(ItemStack stack)
	{
		if(internalStack == null)
		{
			internalStack = stack.copy();
			stack.setCount(0);
			blockEntity.sync();
			return ActionResult.SUCCESS;
		}
		if(stack != null && internalStack.isItemEqual(stack) && ItemStack.areTagsEqual(internalStack, stack))
		{
			int newAmount = Math.min(internalStack.getCount() + stack.getCount(), maxAmount);
			int stackSize = (internalStack.getCount() + stack.getCount()) - newAmount;
			stack.setCount(stackSize);
			internalStack.setCount(newAmount);
			blockEntity.sync();
			return ActionResult.SUCCESS;
		}
		
		return ActionResult.PASS;
	}
	
	public boolean tryInsertIntoInventory(PlayerEntity player, boolean singleItem)
	{
		ItemStack stack = getStack(singleItem);
		if(!stack.isEmpty())
		{
			internalStack.decrement(stack.getCount());
			boolean result = player.inventory.insertStack(stack);
			internalStack.increment(stack.getCount());
			blockEntity.sync();
			return result;
		}
		return false;
	}
	
	public ItemStack getStack(boolean singleItem)
	{
		if(internalStack.getCount() > 0)
		{
			if(singleItem)
			{
				return generateStack(1);
			}
			int stackAmount = Math.min(internalStack.getMaxCount(), internalStack.getCount());
			
			return generateStack(stackAmount);
		}
		
		return ItemStack.EMPTY;
	}
	
	private ItemStack generateStack(int amount)
	{
		ItemStack result = internalStack.copy();
		result.setCount(amount);
		return result;
	}
	
	public boolean isEmpty()
	{
		return internalStack.isEmpty();
	}
	
	public CompoundTag toNBT(CompoundTag tag)
	{
		tag.put("Item", ItemUtils.toTag(new CompoundTag(), internalStack));
//		tag.putInt("Amount", itemType);
		tag.putInt("MaxAmount", maxAmount);
		return tag;
	}
	
	public static ItemHolder fromNBT(CompoundTag tag, BlockEntityClientSerializable blockEntity)
	{
		ItemHolder holder = new ItemHolder();
		holder.internalStack = ItemUtils.fromTag(tag.getCompound("Item"));
		holder.maxAmount = tag.getInt("MaxAmount");
		holder.blockEntity = blockEntity;
		return holder;
	}
	
	public Item getItemType()
	{
		return internalStack.getItem();
	}
	
	public int getAmount()
	{
		return internalStack.getCount();
	}
	
	static {
		FORMAT.setGroupingUsed(true);
	}
	
}

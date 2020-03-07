package me.benfah.simpledrawers.block.entity.holder;


import java.text.NumberFormat;
import java.util.Locale;

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
	
	private ItemStack itemType;
	private int amount;
	private int maxAmount;
	private BlockEntityClientSerializable blockEntity;
	
	
	
	public ItemHolder(Item itemType, int amount, int maxAmount, BlockEntityClientSerializable blockEntity)
	{
		this.itemType = new ItemStack(itemType);
		this.amount = amount;
		this.maxAmount = maxAmount;
		this.blockEntity = blockEntity;
	}
	
	private ItemHolder() {}
	
	public ItemHolder(Item itemType, int maxAmount, BlockEntityClientSerializable blockEntity)
	{
		this(itemType, 0, maxAmount, blockEntity);
	}
	
	public String getDisplayAmount()
	{
		return FORMAT.format(amount);
	}
	
	public ActionResult offer(ItemStack stack)
	{
		if(stack != null && itemType.isItemEqual(stack))
		{
			int newAmount = Math.min(amount + stack.getCount(), maxAmount);
			int stackSize = (amount + stack.getCount()) - newAmount;
			stack.setCount(stackSize);
			amount = newAmount;
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
			amount = amount - stack.getCount();
			boolean result = player.inventory.insertStack(stack);
			amount = amount + stack.getCount();
			blockEntity.sync();
			return result;
		}
		return false;
	}
	
	public ItemStack getStack(boolean singleItem)
	{
		if(amount > 0)
		{
			if(singleItem)
			{
				return generateStack(1);
			}
			int stackAmount = Math.min(itemType.getMaxCount(), amount);
			
			return generateStack(stackAmount);
		}
		
		return ItemStack.EMPTY;
	}
	
	private ItemStack generateStack(int amount)
	{
		ItemStack result = itemType.copy();
		result.setCount(amount);
		return result;
	}
	
	public boolean isEmpty()
	{
		return amount <= 0;
	}
	
	public CompoundTag toNBT(CompoundTag tag)
	{
		tag.put("Item", itemType.toTag(new CompoundTag()));
		tag.putInt("Amount", amount);
		tag.putInt("MaxAmount", maxAmount);
		return tag;
	}
	
	public static ItemHolder fromNBT(CompoundTag tag, BlockEntityClientSerializable blockEntity)
	{
		ItemHolder holder = new ItemHolder();
		holder.itemType = ItemStack.fromTag(tag.getCompound("Item"));
		holder.maxAmount = tag.getInt("MaxAmount");
		holder.amount = tag.getInt("Amount");
		holder.blockEntity = blockEntity;
		return holder;
	}
	
	public Item getItemType()
	{
		return itemType.getItem();
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	static {
		FORMAT.setGroupingUsed(true);
	}
	
}

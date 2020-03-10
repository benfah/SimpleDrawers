package me.benfah.simpledrawers.block.entity.holder;


import java.text.NumberFormat;
import java.util.Locale;

import me.benfah.simpledrawers.utils.ItemUtils;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class ItemHolder implements SidedInventory
{
	public static NumberFormat FORMAT = NumberFormat.getInstance(Locale.US);
	
	private ItemStack internalStack = ItemStack.EMPTY;
	private int maxStacks;
	private BlockEntityClientSerializable blockEntity;
	private ItemStack transferStack;
		
	public ItemHolder(int maxStacks, BlockEntityClientSerializable blockEntity)
	{
//		this.internalStack = new ItemStack(itemType, 1);
		this.maxStacks = maxStacks;
		this.blockEntity = blockEntity;
	}
	
	private ItemHolder() {}
	
	public String getDisplayAmount()
	{
		return FORMAT.format(internalStack.getCount());
	}
	
	public ActionResult offer(ItemStack stack)
	{
		if(internalStack == null || internalStack.isEmpty())
		{
			internalStack = stack.copy();
			stack.setCount(0);
			blockEntity.sync();
			return ActionResult.SUCCESS;
		}
		if(stack != null && internalStack.isItemEqual(stack) && ItemStack.areTagsEqual(internalStack, stack))
		{
			int newAmount = Math.min(internalStack.getCount() + stack.getCount(), getMaxAmount());
			int stackSize = (internalStack.getCount() + stack.getCount()) - newAmount;
			stack.setCount(stackSize);
			internalStack.setCount(newAmount);
			blockEntity.sync();
			return ActionResult.SUCCESS;
		}
		
		return ActionResult.CONSUME;
	}
	
	public int getMaxAmount()
	{
		return internalStack.getItem().getMaxCount() * maxStacks;
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
		tag.putInt("MaxAmount", maxStacks);
		return tag;
	}
	
	public static ItemHolder fromNBT(CompoundTag tag, BlockEntityClientSerializable blockEntity)
	{
		ItemHolder holder = new ItemHolder();
		holder.internalStack = ItemUtils.fromTag(tag.getCompound("Item"));
		holder.maxStacks = tag.getInt("MaxAmount");
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
	
	
	
	
	
	
	@Override
	public void clear()
	{
		internalStack.setCount(0);
	}

	@Override
	public int getInvSize()
	{
		return 1;
	}

	@Override
	public boolean isInvEmpty()
	{
		return internalStack.isEmpty();
	}

	@Override
	public ItemStack getInvStack(int slot)
	{
		if(transferStack == null)
		transferStack = new ItemStack(internalStack.getItem());
		return transferStack;
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount)
	{
		if(internalStack.getCount() >= amount)
		{
			ItemStack result = generateStack(amount);
			internalStack.decrement(amount);
			return result;
		}
		return null;
	}

	@Override
	public ItemStack removeInvStack(int slot)
	{
		int maxAmount = internalStack.getItem().getMaxCount();
		
		if(internalStack.getCount() >= maxAmount)
		{
			ItemStack result = generateStack(maxAmount);
			internalStack.decrement(maxAmount);
			return result;
		}
		return null;
	}

	@Override
	public void setInvStack(int slot, ItemStack stack)
	{
		offer(stack);
	}

	@Override
	public void markDirty()
	{
		blockEntity.sync();
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player)
	{
		return false;
	}

	@Override
	public int[] getInvAvailableSlots(Direction side)
	{
		int[] result = new int[32];
		
		for(int i = 0; i < 32; i++)
		{
			result[i] = i;
		}
		
		return result;
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
	{
		return internalStack.getCount() <= getMaxAmount();
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
	{
		return !isEmpty();
	}
	
	public void transferImminentStack()
	{
		if(transferStack != null && !transferStack.isEmpty())
		{
			if(transferStack.getCount() > 1)
			{
				transferStack.decrement(1);
				offer(transferStack);
				transferStack.setCount(1);
			}
		}
	}
	
}

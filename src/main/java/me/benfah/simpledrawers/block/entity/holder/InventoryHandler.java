package me.benfah.simpledrawers.block.entity.holder;

import me.benfah.simpledrawers.hooks.ItemStackHooks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;

public class InventoryHandler implements SidedInventory
{

	ItemHolder holder;

	public InventoryHandler(ItemHolder holder)
	{
		this.holder = holder;

	}

	@Override
	public int getInvSize()
	{
		return 1;
	}

	@Override
	public boolean isInvEmpty()
	{
		return holder.isEmpty();
	}

	@Override
	public ItemStack getInvStack(int slot)
	{
		ItemStack stack = holder.getStack(false);
		ItemStackHooks.addCountChangeConsumer(stack, (newCount, oldCount) -> {
			int difference = oldCount - newCount;
			holder.amount = holder.amount + difference;
		});
		return stack;
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount)
	{
		ItemStack stack = holder.generateStack(Math.min(amount, holder.getItemType().getMaxCount()));
		holder.amount = holder.amount - amount;
		return stack;
	}

	@Override
	public ItemStack removeInvStack(int slot)
	{
		ItemStack stack = holder.getStack(false);
		holder.amount = holder.amount - stack.getCount();
		return stack;
	}

	@Override
	public void setInvStack(int slot, ItemStack stack)
	{
		holder.offer(stack);
	}

	@Override
	public void markDirty()
	{
		holder.blockEntity.sync();
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player)
	{
		return false;
	}

	@Override
	public void clear()
	{
		holder.amount = 0;
	}

	@Override
	public int[] getInvAvailableSlots(Direction side)
	{
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
	{
		return holder.isEmpty() || (!holder.isFull() && holder.isStackEqual(stack));
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
	{
		return !holder.isEmpty();
	}
}

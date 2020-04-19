package me.benfah.simpledrawers.api.drawer.holder;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CombinedInventoryHandler implements SidedInventory
{

	Supplier<List<ItemHolder>> holderSupplier;
	
	List<Pair<ItemHolder, Integer>> slotMap = new ArrayList<>();
	
	public CombinedInventoryHandler(Supplier<List<ItemHolder>> holderSupplier)
	{
		this.holderSupplier = holderSupplier;
		generateSlotList();
	}

	public void generateSlotList()
	{
		slotMap.clear();
		for (ItemHolder holder : holderSupplier.get())
		{
			for (int i : holder.getInventoryHandler().getAvailableSlots(null))
				slotMap.add(new Pair<>(holder, i));
		}

	}

	@Override
	public int size()
	{
		return slotMap.size();
	}

	@Override
	public boolean isEmpty()
	{
		return holderSupplier.get().stream().allMatch((holder) -> holder.getInventoryHandler().isEmpty());
	} 

	@Override
	public ItemStack getStack(int slot)
	{
		Pair<ItemHolder, Integer> pair = slotMap.get(slot);
		return pair.getFirst().getInventoryHandler().getStack(pair.getSecond());
	}

	@Override
	public ItemStack removeStack(int slot, int amount)
	{
		Pair<ItemHolder, Integer> pair = slotMap.get(slot);
		return pair.getFirst().getInventoryHandler().removeStack(pair.getSecond(), amount);
	}

	@Override
	public ItemStack removeStack(int slot)
	{
		Pair<ItemHolder, Integer> pair = slotMap.get(slot);
		return pair.getFirst().getInventoryHandler().removeStack(pair.getSecond());
	}

	@Override
	public void setStack(int slot, ItemStack stack)
	{
		Pair<ItemHolder, Integer> pair = slotMap.get(slot);
		pair.getFirst().getInventoryHandler().setStack(pair.getSecond(), stack);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return false;
	}

	@Override
	public void clear()
	{
		
	}

	@Override
	public int[] getAvailableSlots(Direction side)
	{
		return IntStream.rangeClosed(1, slotMap.size()).map((i) -> i - 1).toArray();
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir)
	{
		Pair<ItemHolder, Integer> pair = slotMap.get(slot);
		return pair.getFirst().getInventoryHandler().canInsert(pair.getSecond(), stack, dir);
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir)
	{
		Pair<ItemHolder, Integer> pair = slotMap.get(slot);
		return pair.getFirst().getInventoryHandler().canExtract(pair.getSecond(), stack, dir);
	}

	@Override
	public void markDirty()
	{
		holderSupplier.get().forEach((holder) -> holder.blockEntity.sync());
	}

}

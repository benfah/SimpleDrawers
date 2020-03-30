package me.benfah.simpledrawers.block.entity;

import java.util.Arrays;
import java.util.List;

import me.benfah.simpledrawers.api.drawer.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.CombinedInventoryHandler;
import me.benfah.simpledrawers.api.drawer.holder.InventoryHandler;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.init.SDBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

public class BlockEntityBasicDrawer extends BlockEntityAbstractDrawer implements BlockEntityClientSerializable, Tickable
{

	private ItemHolder holder = new ItemHolder(32, this);
	
	private CombinedInventoryHandler handler = new CombinedInventoryHandler(() -> Arrays.asList(holder));
	
	public BlockEntityBasicDrawer()
	{
		super(SDBlockEntities.BASIC_DRAWER);
	}

	@Override
	public ItemHolder getItemHolderAt(float x, float y)
	{
		return holder;
	}

	@Override
	public List<ItemHolder> getItemHolders()
	{
		return Arrays.asList(holder);
	}

	@Override
	protected void setItemHolders(List<ItemHolder> holders)
	{
		holder = holders.get(0);
	}

	@Override
	public CombinedInventoryHandler getInventoryHandler()
	{
		return handler;
	}

}

package me.benfah.simpledrawers.block.entity;

import me.benfah.simpledrawers.block.entity.holder.ItemHolder;
import me.benfah.simpledrawers.init.SDBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BlockEntityBasicDrawer extends BlockEntity implements BlockEntityClientSerializable, Tickable
{
	
	private ItemHolder holder = new ItemHolder(32, this);
	
	public BlockEntityBasicDrawer()
	{
		super(SDBlockEntities.BASIC_DRAWER);
	}
	
	
	@Override
	public void fromClientTag(CompoundTag tag)
	{
		if(tag.contains("Holder"))
			holder = ItemHolder.fromNBT(tag.getCompound("Holder"), this);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		if(holder != null)
			tag.put("Holder", holder.toNBT(new CompoundTag()));
			return tag;
	}
	
	public ItemHolder getHolder()
	{
		return holder;
	}
	
	public void setHolder(ItemHolder holder)
	{
		this.holder = holder;
	}
	
	
	@Override
	public void fromTag(CompoundTag tag)
	{
		super.fromTag(tag);
		fromClientTag(tag);
	}
	
	@Override
	public void sync()
	{
		BlockEntityClientSerializable.super.sync();
		markDirty();
	}
	
	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);
		return toClientTag(tag);
	}


	@Override
	public void tick()
	{
		holder.transferImminentStack();
	}
}

package me.benfah.simpledrawers.block.entity;

import me.benfah.simpledrawers.block.entity.holder.ItemHolder;
import me.benfah.simpledrawers.init.SDBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class BlockEntityBasicDrawer extends BlockEntity implements BlockEntityClientSerializable
{
	
	private ItemHolder holder;
	
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
}

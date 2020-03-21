package me.benfah.simpledrawers.block.entity.holder;

import java.text.NumberFormat;
import java.util.Locale;

import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.models.border.BorderRegistry;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class ItemHolder implements SidedInventory
{
	public static NumberFormat FORMAT = NumberFormat.getInstance(Locale.US);

	private Item itemType;
	private CompoundTag tag;
	private int amount;
	private int maxStacks;
	private BlockEntityBasicDrawer blockEntity;
	private ItemStack transferStack;
	private boolean locked = false;

	public ItemHolder(int maxStacks, BlockEntityBasicDrawer blockEntity)
	{
		this.maxStacks = maxStacks;
		this.blockEntity = blockEntity;
	}

	private ItemHolder()
	{
	}

	public String getDisplayAmount()
	{
		return FORMAT.format(amount);
	}

	public ActionResult offer(ItemStack stack)
	{
		if (itemType == null || (amount <= 0 && !locked))
		{
			itemType = stack.getItem();
			tag = stack.getOrCreateTag();
			amount = stack.getCount();
			stack.setCount(0);
			blockEntity.sync();
			return ActionResult.SUCCESS;
		}
		if (stack != null && isStackEqual(stack))
		{
			int newAmount = Math.min(amount + stack.getCount(), getMaxAmount());
			int stackSize = (amount + stack.getCount()) - newAmount;
			stack.setCount(stackSize);
			amount = newAmount;
			blockEntity.sync();
			UnbakedModel model;
			return ActionResult.SUCCESS;
		}

		return ActionResult.CONSUME;
	}

	public int getMaxAmount()
	{
		return itemType.getMaxCount() * maxStacks * blockEntity.getCachedState().get(BorderRegistry.BORDER_TYPE).getStackMultiplier();
	}

	public boolean tryInsertIntoInventory(PlayerEntity player, boolean singleItem)
	{
		ItemStack stack = getStack(singleItem);
		if (!stack.isEmpty())
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
		if (amount > 0)
		{
			if (singleItem)
			{
				return generateStack(1);
			}
			int stackAmount = Math.min(itemType.getMaxCount(), amount);

			return generateStack(stackAmount);
		}

		return ItemStack.EMPTY;
	}

	public ItemStack generateStack(int amount)
	{
		ItemStack stack = new ItemStack(itemType, amount);
		stack.setTag(tag);
		return stack;
	}

	public boolean isEmpty()
	{
		return amount <= 0;
	}

	public boolean isLocked()
	{
		return locked;
	}

	public void setLocked(boolean locked)
	{
		this.locked = locked;
		blockEntity.sync();
	}

	public CompoundTag toNBT(CompoundTag tag)
	{
		tag.put("Item", serializeItemData(new CompoundTag()));
		tag.putInt("MaxAmount", maxStacks);
		tag.putBoolean("Locked", locked);

		return tag;
	}

	public static ItemHolder fromNBT(CompoundTag tag, BlockEntityBasicDrawer blockEntity)
	{
		ItemHolder holder = new ItemHolder();
		holder.deserializeItemData(tag.getCompound("Item"));
		holder.maxStacks = tag.getInt("MaxAmount");
		holder.blockEntity = blockEntity;

		if (tag.contains("Locked"))
			holder.locked = tag.getBoolean("Locked");

		return holder;
	}

	public CompoundTag serializeItemData(CompoundTag tag)
	{
		if(itemType != null)
		{
			tag.putString("id", Registry.ITEM.getId(itemType).toString());
			tag.putInt("Count", amount);
			tag.put("tag", this.tag);
		}
		else
			tag.putString("id", Registry.ITEM.getDefaultId().toString());
		
		return tag;
	}

	public void deserializeItemData(CompoundTag tag)
	{
		Identifier id = new Identifier(tag.getString("id"));
		if (Registry.ITEM.containsId(id))
		{
			itemType = Registry.ITEM.get(id);
			if(itemType != Items.AIR)
			{
				amount = tag.getInt("Count");
				this.tag = tag.getCompound("tag");
			}
			else
			itemType = null;	
			
		}
	}

	public Item getItemType()
	{
		return itemType;
	}

	public int getAmount()
	{
		return amount;
	}
	
	public CompoundTag getTag()
	{
		return tag;
	}
	
	static
	{
		FORMAT.setGroupingUsed(true);
	}

	@Override
	public void clear()
	{
		amount = 0;
	}

	@Override
	public int getInvSize()
	{
		return 1;
	}

	@Override
	public boolean isInvEmpty()
	{
		return isEmpty();
	}

	@Override
	public ItemStack getInvStack(int slot)
	{
		if (transferStack == null)
			transferStack = new ItemStack(itemType);
		return transferStack;
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount)
	{
		int maxAmount = Math.min(itemType.getMaxCount(), amount);
		if (!isEmpty())
		{
			ItemStack result = generateStack(maxAmount);
			this.amount = this.amount - maxAmount;
			return result;
		}
		return null;
	}

	@Override
	public ItemStack removeInvStack(int slot)
	{
		int maxAmount = Math.min(itemType.getMaxCount(), amount);

		if (!isEmpty())
		{
			ItemStack result = generateStack(maxAmount);
			amount = amount - maxAmount;
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

		for (int i = 0; i < 32; i++)
		{
			result[i] = i;
		}

		return result;
	}

	public boolean isStackEqual(ItemStack stack)
	{

		if (stack.getItem().equals(itemType))
		{
			CompoundTag tag1 = this.tag == null ? new CompoundTag() : tag;
			CompoundTag tag2 = stack.getTag() == null ? new CompoundTag() : stack.getTag();
			return tag1.equals(tag2);
		}
		return false;
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
	{
		return isEmpty() || isStackEqual(stack);
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
	{
		return !isEmpty();
	}

	public void transferImminentStack()
	{
		if (transferStack != null && !transferStack.isEmpty())
		{
			if (transferStack.getCount() > 1)
			{
				transferStack.decrement(1);
				offer(transferStack);
				transferStack.setCount(1);
			}
		}
	}

}

package me.benfah.simpledrawers.api.drawer.holder;

import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.utils.NumberUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemHolder
{

    protected Item itemType;
    protected CompoundTag tag;
    protected int amount;
    protected int maxStacks;
    protected BlockEntityAbstractDrawer blockEntity;

    private InventoryHandler handler;

    private boolean locked = false;

    public ItemHolder(int maxStacks, BlockEntityAbstractDrawer blockEntity)
    {
        this();
        this.maxStacks = maxStacks;
        this.blockEntity = blockEntity;
    }

    private ItemHolder()
    {
        this.handler = new InventoryHandler(this);
    }

    public String getDisplayAmount()
    {
        return NumberUtils.displayNumber(amount);
    }

    public ActionResult offer(ItemStack stack)
    {
        if(shouldOffer(stack))
        {
            if(itemType == null || (amount <= 0 && !locked))
            {
                itemType = stack.getItem();
                amount = stack.getCount();
                tag = stack.getTag();

                stack.setCount(0);
                blockEntity.sync();
                return ActionResult.SUCCESS;
            }
            if(isStackEqual(stack))
            {
                int newAmount = Math.min(amount + stack.getCount(), getMaxAmount());
                int stackSize = (amount + stack.getCount()) - newAmount;
                stack.setCount(stackSize);
                amount = newAmount;
                blockEntity.sync();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    public boolean shouldOffer(ItemStack stack)
    {
        return stack != null && !stack.isEmpty()
                && ((itemType == null || (amount <= 0 && !locked)) || isStackEqual(stack));
    }

    public int getMaxAmount()
    {
        return itemType.getMaxCount() * getMaxStacks();
    }

    public int getMaxStacks()
    {
        return maxStacks * blockEntity.getCachedState().get(BorderRegistry.BORDER_TYPE).getStackMultiplier();
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
        if(!isEmpty())
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

    public ItemStack generateStack(int amount)
    {
        ItemStack stack = new ItemStack(itemType, amount);
        if(tag != null)
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

    public static ItemHolder fromNBT(CompoundTag tag, BlockEntityAbstractDrawer blockEntity)
    {
        ItemHolder holder = new ItemHolder();

        holder.deserializeItemData(tag.getCompound("Item"));
        holder.maxStacks = tag.getInt("MaxAmount");
        holder.blockEntity = blockEntity;

        if(tag.contains("Locked"))
            holder.locked = tag.getBoolean("Locked");

        return holder;
    }

    public CompoundTag serializeItemData(CompoundTag tag)
    {
        if(itemType != null)
        {
            tag.putString("id", Registry.ITEM.getId(itemType).toString());
            tag.putInt("Count", amount);
            if(this.tag != null && !this.tag.isEmpty())
                tag.put("tag", this.tag);
        } else
            tag.putString("id", Registry.ITEM.getDefaultId().toString());

        return tag;
    }

    public void deserializeItemData(CompoundTag tag)
    {
        Identifier id = new Identifier(tag.getString("id"));

        itemType = Registry.ITEM.get(id);
        if(itemType != Items.AIR)
        {
            amount = tag.getInt("Count");
            if(tag.contains("tag") && !tag.getCompound("tag").isEmpty())
                this.tag = tag.getCompound("tag");
        } else
            itemType = null;

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

    public boolean isFull()
    {
        return amount >= getMaxAmount();
    }

    public InventoryHandler getInventoryHandler()
    {
        return handler;
    }

    public boolean isStackEqual(ItemStack stack)
    {

        if(stack.getItem().equals(itemType))
        {
            CompoundTag tag1 = this.tag == null ? new CompoundTag() : tag;
            CompoundTag tag2 = stack.getTag() == null ? new CompoundTag() : stack.getTag();
            return tag1.equals(tag2);
        }
        return false;
    }

}

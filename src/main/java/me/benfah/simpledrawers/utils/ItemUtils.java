package me.benfah.simpledrawers.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemUtils
{

    public static CompoundTag toTag(CompoundTag tag, ItemStack stack)
    {
        Identifier identifier = Registry.ITEM.getId(stack.getItem());
        tag.putString("id", identifier == null ? "minecraft:air" : identifier.toString());
        tag.putInt("Count", stack.getCount());
        if(stack.getTag() != null)
        {
            tag.put("tag", stack.getTag().copy());
        }

        return tag;
    }

    public static ItemStack fromTag(CompoundTag tag)
    {
        Item i = Registry.ITEM.get(new Identifier(tag.getString("id")));
        ItemStack result = new ItemStack(i);
        result.setCount(tag.getInt("Count"));
        if(tag.contains("tag"))
            result.setTag(tag.getCompound("tag"));
        return result;
    }

}

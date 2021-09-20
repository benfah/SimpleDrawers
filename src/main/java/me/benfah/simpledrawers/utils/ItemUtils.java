package me.benfah.simpledrawers.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemUtils
{

    public static NbtCompound toTag(NbtCompound tag, ItemStack stack)
    {
        Identifier identifier = Registry.ITEM.getId(stack.getItem());
        tag.putString("id", identifier.toString());
        tag.putInt("Count", stack.getCount());
        if(stack.getNbt() != null)
        {
            tag.put("tag", stack.getNbt().copy());
        }

        return tag;
    }

    public static ItemStack fromTag(NbtCompound tag)
    {
        Item i = Registry.ITEM.get(new Identifier(tag.getString("id")));
        ItemStack result = new ItemStack(i);
        result.setCount(tag.getInt("Count"));
        if(tag.contains("tag"))
            result.setNbt(tag.getCompound("tag"));
        return result;
    }

}

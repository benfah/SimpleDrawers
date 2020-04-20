package me.benfah.simpledrawers.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class SDTags
{

    public static Tag<Item> DRAWERS_ITEM;
    public static Tag<Block> DRAWERS_BLOCK;

    public static void init()
    {
        DRAWERS_ITEM = ItemTags.getContainer().getOrCreate(new Identifier("simpledrawers:drawers"));
        DRAWERS_BLOCK = BlockTags.getContainer().getOrCreate(new Identifier("simpledrawers:drawers"));

    }

}

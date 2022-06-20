package me.benfah.simpledrawers.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SDTags
{

    public static TagKey<Item> DRAWERS_ITEM;
    public static TagKey<Block> DRAWERS_BLOCK;

    public static void init()
    {
        DRAWERS_ITEM = TagKey.of(Registry.ITEM_KEY, new Identifier("simpledrawers:drawers"));
        DRAWERS_BLOCK = TagKey.of(Registry.BLOCK_KEY, new Identifier("simpledrawers:drawers"));
    }

}

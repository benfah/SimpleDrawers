package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SDItems
{
	
	private SDItems() { }
	
	public static BlockItem OAK_DRAWER = new BlockItem(SDBlocks.OAK_DRAWER, new Settings());
	public static BlockItem BIRCH_DRAWER = new BlockItem(SDBlocks.BIRCH_DRAWER, new Settings());
	public static BlockItem SPRUCE_DRAWER = new BlockItem(SDBlocks.SPRUCE_DRAWER, new Settings());
	public static BlockItem JUNGLE_DRAWER = new BlockItem(SDBlocks.JUNGLE_DRAWER, new Settings());
	public static BlockItem ACACIA_DRAWER = new BlockItem(SDBlocks.ACACIA_DRAWER, new Settings());
	public static BlockItem DARK_OAK_DRAWER = new BlockItem(SDBlocks.DARK_OAK_DRAWER, new Settings());

	
	
	public static void init()
	{
		registerBlockItem(SDBlocks.OAK_DRAWER);
		registerBlockItem(SDBlocks.BIRCH_DRAWER);
		registerBlockItem(SDBlocks.SPRUCE_DRAWER);
		registerBlockItem(SDBlocks.JUNGLE_DRAWER);
		registerBlockItem(SDBlocks.ACACIA_DRAWER);
		registerBlockItem(SDBlocks.DARK_OAK_DRAWER);

	}
	
	public static BlockItem registerBlockItem(Block block)
	{
		BlockItem blockItem = new BlockItem(block, new Settings().group(ItemGroup.DECORATIONS));
		Identifier identifier = Registry.BLOCK.getId(block);
		return Registry.register(Registry.ITEM, identifier, blockItem);
	}
	
}

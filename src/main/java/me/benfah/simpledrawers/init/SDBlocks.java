package me.benfah.simpledrawers.init;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.block.BlockBasicDrawer;
import me.benfah.simpledrawers.block.BlockDrawerController;
import me.benfah.simpledrawers.models.border.Border;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SDBlocks
{

	private SDBlocks()
	{
	}

	public static BlockBasicDrawer OAK_DRAWER = new BlockBasicDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.OAK_BORDER);
	public static BlockBasicDrawer BIRCH_DRAWER = new BlockBasicDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.BIRCH_BORDER);
	public static BlockBasicDrawer SPRUCE_DRAWER = new BlockBasicDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.SPRUCE_BORDER);
	public static BlockBasicDrawer JUNGLE_DRAWER = new BlockBasicDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.JUNGLE_BORDER);
	public static BlockBasicDrawer ACACIA_DRAWER = new BlockBasicDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.ACACIA_BORDER);
	public static BlockBasicDrawer DARK_OAK_DRAWER = new BlockBasicDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.DARK_OAK_BORDER);

	public static BlockDrawerController DRAWER_CONTROLLER = new BlockDrawerController(
			FabricBlockSettings.of(Material.STONE).build());

	public static void init()
	{
		registerBlock("oak_drawer", OAK_DRAWER);
		registerBlock("spruce_drawer", SPRUCE_DRAWER);
		registerBlock("birch_drawer", BIRCH_DRAWER);
		registerBlock("jungle_drawer", JUNGLE_DRAWER);
		registerBlock("acacia_drawer", ACACIA_DRAWER);
		registerBlock("dark_oak_drawer", DARK_OAK_DRAWER);
		
		registerBlock("drawer_controller", DRAWER_CONTROLLER);
		
	}

	private static void registerBlock(String name, Block block)
	{
		Registry.register(Registry.BLOCK, new Identifier(SimpleDrawersMod.MOD_ID, name), block);
	}

	public static List<Block> getBlocks()
	{
		List<Block> result = new ArrayList<>();
		for (Field f : SDBlocks.class.getFields())
		{
			if (f.getType().isAssignableFrom(Block.class))
				try
				{
					result.add((Block) f.get(null));
				} catch (ReflectiveOperationException e)
				{
					e.printStackTrace();
				}
		}
		return result;
	}

}

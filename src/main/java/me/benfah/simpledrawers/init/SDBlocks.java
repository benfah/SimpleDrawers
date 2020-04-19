package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.block.BlockDrawer;
import me.benfah.simpledrawers.block.BlockDrawerController;
import me.benfah.simpledrawers.block.BlockHalfDrawer;
import me.benfah.simpledrawers.block.BlockQuadDrawer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SDBlocks
{

	private SDBlocks()
	{
	}

	public static BlockDrawer OAK_DRAWER = new BlockDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.OAK_BORDER);
	public static BlockDrawer BIRCH_DRAWER = new BlockDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.BIRCH_BORDER);
	public static BlockDrawer SPRUCE_DRAWER = new BlockDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.SPRUCE_BORDER);
	public static BlockDrawer JUNGLE_DRAWER = new BlockDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.JUNGLE_BORDER);
	public static BlockDrawer ACACIA_DRAWER = new BlockDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.ACACIA_BORDER);
	public static BlockDrawer DARK_OAK_DRAWER = new BlockDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.DARK_OAK_BORDER);

	public static BlockHalfDrawer HALF_OAK_DRAWER = new BlockHalfDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.OAK_BORDER);
	public static BlockHalfDrawer HALF_BIRCH_DRAWER = new BlockHalfDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.BIRCH_BORDER);
	public static BlockHalfDrawer HALF_SPRUCE_DRAWER = new BlockHalfDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.SPRUCE_BORDER);
	public static BlockHalfDrawer HALF_JUNGLE_DRAWER = new BlockHalfDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.JUNGLE_BORDER);
	public static BlockHalfDrawer HALF_ACACIA_DRAWER = new BlockHalfDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.ACACIA_BORDER);
	public static BlockHalfDrawer HALF_DARK_OAK_DRAWER = new BlockHalfDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.DARK_OAK_BORDER);
	
	public static BlockQuadDrawer QUAD_OAK_DRAWER = new BlockQuadDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.OAK_BORDER);
	public static BlockQuadDrawer QUAD_BIRCH_DRAWER = new BlockQuadDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.BIRCH_BORDER);
	public static BlockQuadDrawer QUAD_SPRUCE_DRAWER = new BlockQuadDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.SPRUCE_BORDER);
	public static BlockQuadDrawer QUAD_JUNGLE_DRAWER = new BlockQuadDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.JUNGLE_BORDER);
	public static BlockQuadDrawer QUAD_ACACIA_DRAWER = new BlockQuadDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.ACACIA_BORDER);
	public static BlockQuadDrawer QUAD_DARK_OAK_DRAWER = new BlockQuadDrawer(
			FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build(),
			Border.DARK_OAK_BORDER);
	
	public static BlockDrawerController DRAWER_CONTROLLER = new BlockDrawerController(
			FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.0F).build());

	public static void init()
	{
		registerBlock("oak_drawer", OAK_DRAWER);
		registerBlock("spruce_drawer", SPRUCE_DRAWER);
		registerBlock("birch_drawer", BIRCH_DRAWER);
		registerBlock("jungle_drawer", JUNGLE_DRAWER);
		registerBlock("acacia_drawer", ACACIA_DRAWER);
		registerBlock("dark_oak_drawer", DARK_OAK_DRAWER);

		registerBlock("half_oak_drawer", HALF_OAK_DRAWER);
		registerBlock("half_spruce_drawer", HALF_SPRUCE_DRAWER);
		registerBlock("half_birch_drawer", HALF_BIRCH_DRAWER);
		registerBlock("half_jungle_drawer", HALF_JUNGLE_DRAWER);
		registerBlock("half_acacia_drawer", HALF_ACACIA_DRAWER);
		registerBlock("half_dark_oak_drawer", HALF_DARK_OAK_DRAWER);
		
		registerBlock("quad_oak_drawer", QUAD_OAK_DRAWER);
		registerBlock("quad_spruce_drawer", QUAD_SPRUCE_DRAWER);
		registerBlock("quad_birch_drawer", QUAD_BIRCH_DRAWER);
		registerBlock("quad_jungle_drawer", QUAD_JUNGLE_DRAWER);
		registerBlock("quad_acacia_drawer", QUAD_ACACIA_DRAWER);
		registerBlock("quad_dark_oak_drawer", QUAD_DARK_OAK_DRAWER);
		
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

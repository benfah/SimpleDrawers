package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.block.BlockBasicDrawer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class SDBlocks
{
	
	private SDBlocks() { }
	
	public static BlockBasicDrawer OAK_DRAWER = new BlockBasicDrawer(FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build());
	public static BlockBasicDrawer BIRCH_DRAWER = new BlockBasicDrawer(FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build());
	public static BlockBasicDrawer SPRUCE_DRAWER = new BlockBasicDrawer(FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build());
	public static BlockBasicDrawer JUNGLE_DRAWER = new BlockBasicDrawer(FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build());
	public static BlockBasicDrawer ACACIA_DRAWER = new BlockBasicDrawer(FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build());
	public static BlockBasicDrawer DARK_OAK_DRAWER = new BlockBasicDrawer(FabricBlockSettings.of(Material.WOOD).strength(2f, 2f).sounds(BlockSoundGroup.WOOD).build());

	public static void init()
	{
		register("oak_drawer", OAK_DRAWER);
		register("spruce_drawer", SPRUCE_DRAWER);	
		register("birch_drawer", BIRCH_DRAWER);	
		register("jungle_drawer", JUNGLE_DRAWER);	
		register("acacia_drawer", ACACIA_DRAWER);	
		register("dark_oak_drawer", DARK_OAK_DRAWER);

	}
	
	private static void register(String name, Block block)
	{
		Registry.register(Registry.BLOCK, new Identifier(SimpleDrawersMod.MOD_ID, name), block);
	}
	
}

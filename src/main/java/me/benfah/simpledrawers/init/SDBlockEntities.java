package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityDrawerController;
import me.benfah.simpledrawers.block.entity.BlockEntityHalfDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityQuadDrawer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SDBlockEntities
{

	private SDBlockEntities()
	{
	}

	public static BlockEntityType<BlockEntityBasicDrawer> BASIC_DRAWER = null;

	public static BlockEntityType<BlockEntityHalfDrawer> HALF_DRAWER = null;

	public static BlockEntityType<BlockEntityQuadDrawer> QUAD_DRAWER = null;

	public static BlockEntityType<BlockEntityDrawerController> DRAWER_CONTROLLER = null;

	public static void init()
	{
		BASIC_DRAWER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SimpleDrawersMod.MOD_ID, "drawer"),
				BlockEntityType.Builder.create(BlockEntityBasicDrawer::new, SDBlocks.OAK_DRAWER, SDBlocks.ACACIA_DRAWER,
						SDBlocks.BIRCH_DRAWER, SDBlocks.JUNGLE_DRAWER, SDBlocks.SPRUCE_DRAWER, SDBlocks.DARK_OAK_DRAWER)
						.build(null));

		HALF_DRAWER = Registry.register(Registry.BLOCK_ENTITY_TYPE,
				new Identifier(SimpleDrawersMod.MOD_ID, "half_drawer"),
				BlockEntityType.Builder.create(BlockEntityHalfDrawer::new, SDBlocks.HALF_OAK_DRAWER,
						SDBlocks.HALF_BIRCH_DRAWER, SDBlocks.HALF_ACACIA_DRAWER, SDBlocks.HALF_DARK_OAK_DRAWER,
						SDBlocks.HALF_SPRUCE_DRAWER, SDBlocks.HALF_JUNGLE_DRAWER).build(null));

		QUAD_DRAWER = Registry.register(Registry.BLOCK_ENTITY_TYPE,
				new Identifier(SimpleDrawersMod.MOD_ID, "quad_drawer"),
				BlockEntityType.Builder.create(BlockEntityQuadDrawer::new, SDBlocks.QUAD_OAK_DRAWER,
						SDBlocks.QUAD_BIRCH_DRAWER, SDBlocks.QUAD_ACACIA_DRAWER, SDBlocks.QUAD_DARK_OAK_DRAWER,
						SDBlocks.QUAD_SPRUCE_DRAWER, SDBlocks.QUAD_JUNGLE_DRAWER).build(null));

		DRAWER_CONTROLLER = Registry.register(Registry.BLOCK_ENTITY_TYPE,
				new Identifier(SimpleDrawersMod.MOD_ID, "drawer_controller"), BlockEntityType.Builder
						.create(BlockEntityDrawerController::new, SDBlocks.DRAWER_CONTROLLER).build(null));
	}

}

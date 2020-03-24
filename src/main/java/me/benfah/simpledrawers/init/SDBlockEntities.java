package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityDrawerController;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SDBlockEntities
{

	private SDBlockEntities()
	{
	}

	public static BlockEntityType<BlockEntityBasicDrawer> BASIC_DRAWER = null;

	public static BlockEntityType<BlockEntityDrawerController> DRAWER_CONTROLLER = null;

	
	public static void init()
	{
		BASIC_DRAWER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SimpleDrawersMod.MOD_ID, "drawer"),
				BlockEntityType.Builder.create(BlockEntityBasicDrawer::new, SDBlocks.OAK_DRAWER, SDBlocks.ACACIA_DRAWER,
						SDBlocks.BIRCH_DRAWER, SDBlocks.JUNGLE_DRAWER, SDBlocks.SPRUCE_DRAWER, SDBlocks.DARK_OAK_DRAWER, SDBlocks.CRIMSON_DRAWER, SDBlocks.WARPED_DRAWER)
						.build(null));
		
		DRAWER_CONTROLLER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SimpleDrawersMod.MOD_ID, "drawer_controller"),
				BlockEntityType.Builder.create(BlockEntityDrawerController::new, SDBlocks.DRAWER_CONTROLLER)
						.build(null));
	}

}

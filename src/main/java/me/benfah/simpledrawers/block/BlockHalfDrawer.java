package me.benfah.simpledrawers.block;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.border.BorderRegistry.BorderProperty;
import me.benfah.simpledrawers.api.drawer.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.DrawerType;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityHalfDrawer;
import me.benfah.simpledrawers.item.DrawerInteractable;
import me.benfah.simpledrawers.utils.BlockUtils;
import me.benfah.simpledrawers.utils.model.BorderModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockHalfDrawer extends BlockDrawer
{

	public BlockHalfDrawer(Settings settings, Border border)
	{
		super(settings, border);
		this.setDefaultState(getDefaultState().with(DrawerType.DRAWER_TYPE, DrawerType.HALF));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView arg0)
	{
		return new BlockEntityHalfDrawer();
	}

}

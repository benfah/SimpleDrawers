package me.benfah.simpledrawers.block;

import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.item.DrawerInteractable;
import me.benfah.simpledrawers.item.ItemKey;
import me.benfah.simpledrawers.models.border.Border;
import me.benfah.simpledrawers.models.border.BorderRegistry;
import me.benfah.simpledrawers.models.border.BorderRegistry.BorderProperty;
import me.benfah.simpledrawers.utils.model.BorderModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockBasicDrawer extends BlockWithEntity implements InventoryProvider, BorderModelProvider
{

	public static DirectionProperty FACING = Properties.FACING;
	public static BorderProperty BORDER_TYPE = BorderRegistry.BORDER_TYPE;
	public Identifier borderIdentifier;

	public BlockBasicDrawer(Settings settings, Border border)
	{
		super(settings);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(BORDER_TYPE, border));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView arg0)
	{
		return new BlockEntityBasicDrawer();
	}

	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit)
	{
		if (!world.isClient)
		{
			if (hand.equals(Hand.MAIN_HAND) && !player.getMainHandStack().isEmpty())
			{
				BlockEntityBasicDrawer drawer = (BlockEntityBasicDrawer) world.getBlockEntity(pos);

				if (player.getMainHandStack().getItem() instanceof DrawerInteractable)
				{
					((DrawerInteractable) player.getMainHandStack().getItem()).interact(drawer, player);
					return ActionResult.SUCCESS;
				}

				return drawer.getHolder().offer(player.getMainHandStack());
			}
		}
		return ActionResult.CONSUME;
	}

	@Override
	public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player)
	{
		if (!world.isClient)
		{
			HitResult result = player.rayTrace(4.5, 0, false);
			if (result.getType() == Type.BLOCK)
			{
				BlockHitResult blockHitResult = (BlockHitResult) result;
				if (!blockHitResult.getSide().equals(state.get(FACING)))
					return;
			}
			BlockEntityBasicDrawer blockEntity = (BlockEntityBasicDrawer) world.getBlockEntity(pos);
			if (blockEntity.getHolder() != null)
			{
				blockEntity.getHolder().tryInsertIntoInventory(player, !player.isSneaking());
			}
		}
	}

	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
	{
		BlockEntityBasicDrawer drawer = (BlockEntityBasicDrawer) world.getBlockEntity(pos);

		if (newState.isAir() && !moved)
		{
			if (state.get(BorderRegistry.BORDER_TYPE).getDropItem() != null)
			{
				ItemEntity upgradeEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
						state.get(BorderRegistry.BORDER_TYPE).getDropItem());
				world.spawnEntity(upgradeEntity);
			}
			
			if (!drawer.getHolder().isEmpty())
			{

				ItemStack stack = new ItemStack(drawer.getHolder().getItemType(), drawer.getHolder().getAmount());
				if (drawer.getHolder().getTag() != null)
					stack.setTag(drawer.getHolder().getTag());

				ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack);
				world.spawnEntity(itemEntity);
			}
		}
		super.onBlockRemoved(state, world, pos, newState, moved);
	}

	@Override
	public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos)
	{
		HitResult result = player.rayTrace(4.5, 0, false);
		if (result.getType() == Type.BLOCK)
		{
			BlockHitResult blockHitResult = (BlockHitResult) result;
			if (blockHitResult.getSide().equals(state.get(FACING)))
				return 0;
		}
		return super.calcBlockBreakingDelta(state, player, world, pos);
	}

	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
	}

	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public void appendProperties(Builder<Block, BlockState> builder)
	{
		builder.add(FACING).add(BORDER_TYPE);
	}

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos)
	{
		return ((BlockEntityBasicDrawer) world.getBlockEntity(pos)).getHolder();
	}

	@Override
	public Identifier getBorderModel()
	{
		return borderIdentifier;
	}

}

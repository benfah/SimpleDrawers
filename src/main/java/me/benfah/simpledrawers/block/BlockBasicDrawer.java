package me.benfah.simpledrawers.block;


import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.holder.ItemHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class BlockBasicDrawer extends BlockWithEntity
{

	public static DirectionProperty FACING = Properties.FACING;

	public BlockBasicDrawer(Settings settings)
	{
		super(settings);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
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
				if (drawer.getHolder() == null || drawer.getHolder().isEmpty())
				{
					drawer.setHolder(new ItemHolder(player.getMainHandStack().getItem(), 32 * 64, drawer));
				}
				return drawer.getHolder().offer(player.getMainHandStack());
			}
		}
		return ActionResult.CONSUME;
	}
	
	@Override
	public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player)
	{
		if(!world.isClient)
		{
			HitResult result = player.rayTrace(4.5, 0, false);
			if(result.getType() == Type.BLOCK)
			{
				BlockHitResult blockHitResult = (BlockHitResult) result;
				if(!blockHitResult.getSide().equals(state.get(FACING)))
				return;
			}
			BlockEntityBasicDrawer blockEntity = (BlockEntityBasicDrawer) world.getBlockEntity(pos);
			if(blockEntity.getHolder() != null)
			{
				blockEntity.getHolder().tryInsertIntoInventory(player, !player.isSneaking());
			}
		}
	}
	
	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
	{
		BlockEntityBasicDrawer drawer = (BlockEntityBasicDrawer) world.getBlockEntity(pos);
		ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(drawer.getHolder().getItemType(), drawer.getHolder().getAmount()));
		world.spawnEntity(itemEntity);
		super.onBlockRemoved(state, world, pos, newState, moved);
	}
	
	@Override
	public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos)
	{
		HitResult result = player.rayTrace(4.5, 0, false);
		if(result.getType() == Type.BLOCK)
		{
			BlockHitResult blockHitResult = (BlockHitResult) result;
			if(blockHitResult.getSide().equals(state.get(FACING)))
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
	protected void appendProperties(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

}

package me.benfah.simpledrawers.block;

import me.benfah.simpledrawers.block.entity.BlockEntityDrawerController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

public class BlockDrawerController extends BlockWithEntity implements InventoryProvider
{
	
	public static DirectionProperty FACING = HorizontalFacingBlock.FACING;
	
	public BlockDrawerController(Settings settings)
	{
		super(settings);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}
	
	@Override
	protected void appendProperties(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new BlockEntityDrawerController();
	}

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos)
	{
		return ((BlockEntityDrawerController)world.getBlockEntity(pos)).getInventoryHandler();
	}
	
}

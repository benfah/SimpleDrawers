package me.benfah.simpledrawers.block.entity;

import java.util.ArrayList;
import java.util.List;

import me.benfah.simpledrawers.block.BlockBasicDrawer;
import me.benfah.simpledrawers.block.BlockDrawerController;
import me.benfah.simpledrawers.block.entity.holder.InventoryHandler;
import me.benfah.simpledrawers.init.SDBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockEntityDrawerController extends BlockEntity implements SidedInventory
{

	List<BlockPos> drawerPositions = new ArrayList<>();

	public BlockEntityDrawerController()
	{
		super(SDBlockEntities.DRAWER_CONTROLLER);
	}

	
	public void updateDrawerPositions()
	{
		drawerPositions.clear();
		Direction d = getCachedState().get(BlockDrawerController.FACING).getOpposite();
		BlockPos pos = getPos().offset(d);
		for (int offsetY = 1; offsetY >= -1; offsetY--)
		{
			for (int offset = -1; offset <= 1; offset++)
			{
				BlockPos drawerPos;
				if (d == Direction.NORTH || d == Direction.SOUTH)
					drawerPos = new BlockPos(pos.getX() + offset, pos.getY() + offsetY, pos.getZ());
				else
					drawerPos = new BlockPos(pos.getX(), pos.getY() + offsetY, pos.getZ() + offset);

				if (getWorld().getBlockState(drawerPos).getBlock() instanceof BlockBasicDrawer)
				{
					drawerPositions.add(drawerPos);
				}

			}
		}
	}
	
	private List<BlockPos> getDrawerPositions()
	{
		updateDrawerPositions();
		return drawerPositions;
	}
	
	@Override
	public int getInvSize()
	{
		updateDrawerPositions();
		return getDrawerPositions().size() * 2;
	}

	@Override
	public boolean isInvEmpty()
	{
		return getDrawerPositions().stream()
				.allMatch((drawerPos) -> getInventoryHandler(drawerPos).isInvEmpty());
	}

	@Override
	public ItemStack getInvStack(int slot)
	{
		return getInventoryHandler(getDrawerPositions().get(getListPosition(slot))).getInvStack(getDrawerPosition(slot));
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount)
	{
		return getInventoryHandler(getDrawerPositions().get(getListPosition(slot))).takeInvStack(getDrawerPosition(slot), amount);
	}

	@Override
	public ItemStack removeInvStack(int slot)
	{
		return getInventoryHandler(getDrawerPositions().get(getListPosition(slot))).removeInvStack(getDrawerPosition(slot));
	}

	@Override
	public void setInvStack(int slot, ItemStack stack)
	{
		getInventoryHandler(getDrawerPositions().get(getListPosition(slot))).setInvStack(getDrawerPosition(slot), stack);
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player)
	{
		return false;
	}

	@Override
	public void clear()
	{
		getDrawerPositions().forEach((drawerPos) -> getInventoryHandler(drawerPos).clear());
	}

	@Override
	public int[] getInvAvailableSlots(Direction side)
	{
		int[] availableSlots = new int[getDrawerPositions().size() * 2];
		
		for(int i = 0; i < getDrawerPositions().size() * 2; i++)
		{
			availableSlots[i] = i;
		}
		
		return availableSlots;
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
	{
		return getInventoryHandler(getDrawerPositions().get(getListPosition(slot))).canInsertInvStack(getDrawerPosition(slot), stack, dir);
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
	{
		return getInventoryHandler(getDrawerPositions().get(getListPosition(slot))).canExtractInvStack(getDrawerPosition(slot), stack, dir);
	}
	
	private InventoryHandler getInventoryHandler(BlockPos pos)
	{
		return ((BlockEntityBasicDrawer) world.getBlockEntity(pos)).getHolder().getInventoryHandler();
	}
	
	private int getListPosition(int slot)
	{
		if(slot < getDrawerPositions().size())
		return slot;
		return slot - getDrawerPositions().size();
	}
	private int getDrawerPosition(int slot)
	{
		return slot / getDrawerPositions().size();
	}
	
	@Override
	public void markDirty()
	{
		getDrawerPositions().forEach((drawerPos) -> getInventoryHandler(drawerPos).markDirty());
		super.markDirty();
	}
	
}

package me.benfah.simpledrawers.block.entity;

import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.CombinedInventoryHandler;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.block.BlockDrawerController;
import me.benfah.simpledrawers.init.SDBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockEntityDrawerController extends BlockEntity
{

    List<BlockPos> drawerPositions = new ArrayList<>();

    CombinedInventoryHandler handler = new CombinedInventoryHandler(this::getAffectedItemHolders);

    public BlockEntityDrawerController(BlockPos pos, BlockState state)
    {
        super(SDBlockEntities.DRAWER_CONTROLLER, pos, state);
    }

    public void updateDrawerPositions()
    {
        drawerPositions.clear();
        Direction d = getCachedState().get(BlockDrawerController.FACING).getOpposite();
        BlockPos pos = getPos().offset(d);
        for(int offsetY = 1; offsetY >= -1; offsetY--)
        {
            for(int offset = -1; offset <= 1; offset++)
            {
                BlockPos drawerPos;
                if(d == Direction.NORTH || d == Direction.SOUTH)
                    drawerPos = new BlockPos(pos.getX() + offset, pos.getY() + offsetY, pos.getZ());
                else
                    drawerPos = new BlockPos(pos.getX(), pos.getY() + offsetY, pos.getZ() + offset);

                if(getWorld().getBlockState(drawerPos).getBlock() instanceof BlockAbstractDrawer)
                {
                    drawerPositions.add(drawerPos);
                }

            }
        }
        handler.generateSlotList();

    }

    public List<ItemHolder> getAffectedItemHolders()
    {
        return drawerPositions.stream()
                .flatMap((pos) -> ((BlockEntityAbstractDrawer) world.getBlockEntity(pos)).getItemHolders().stream())
                .collect(Collectors.toList());
    }

    public CombinedInventoryHandler getInventoryHandler()
    {
        return handler;
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntityDrawerController blockEntity)
    {
        blockEntity.updateDrawerPositions();
    }

}

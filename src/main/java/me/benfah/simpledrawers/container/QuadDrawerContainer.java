package me.benfah.simpledrawers.container;

import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityQuadDrawer;
import net.minecraft.entity.player.PlayerEntity;

public class QuadDrawerContainer extends DrawerContainer<BlockEntityQuadDrawer>
{


    public QuadDrawerContainer(int syncId, PlayerEntity entity, BlockEntityQuadDrawer drawer)
    {
        super(syncId, entity, drawer);
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(0), 71, 35));
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(1), 89, 35));
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(2), 71, 53));
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(3), 89, 53));
    }




}

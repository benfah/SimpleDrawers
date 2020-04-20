package me.benfah.simpledrawers.container;

import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import me.benfah.simpledrawers.block.entity.BlockEntityHalfDrawer;
import net.minecraft.entity.player.PlayerEntity;

public class DoubleDrawerContainer extends DrawerContainer<BlockEntityHalfDrawer>
{


    public DoubleDrawerContainer(int syncId, PlayerEntity entity, BlockEntityHalfDrawer drawer)
    {
        super(syncId, entity, drawer);
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(0), 80, 35));
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(1), 80, 53));
    }


}

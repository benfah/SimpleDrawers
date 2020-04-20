package me.benfah.simpledrawers.container;

import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import net.minecraft.entity.player.PlayerEntity;

public class BasicDrawerContainer extends DrawerContainer<BlockEntityBasicDrawer>
{


    public BasicDrawerContainer(int syncId, PlayerEntity entity, BlockEntityBasicDrawer drawer)
    {
        super(syncId, entity, drawer);
        holderSlots.add(new HolderSlot(() -> drawer.getItemHolders().get(0), 80, 44));
    }


}

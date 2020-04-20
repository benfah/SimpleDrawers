package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.minecraft.entity.player.PlayerEntity;

public interface DrawerInteractable
{

    void interact(BlockEntityAbstractDrawer blockEntity, PlayerEntity player, ItemHolder holder);

}

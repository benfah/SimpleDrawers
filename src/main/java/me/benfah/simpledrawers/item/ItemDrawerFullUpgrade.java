package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class ItemDrawerFullUpgrade extends Item implements DrawerInteractable
{

    Border upgradeTo;


    public ItemDrawerFullUpgrade(Settings settings, Border upgradeTo)
    {
        super(settings);
        this.upgradeTo = upgradeTo;
    }

    @Override
    public void interact(BlockEntityAbstractDrawer blockEntity, PlayerEntity player, ItemHolder holder)
    {
        Border currentBorder = blockEntity.getCachedState().get(BorderRegistry.BORDER_TYPE);

        if(upgradeTo.compareTo(currentBorder) > 0)
        {
            upgrade(blockEntity, player);

        }
    }

    public void upgrade(BlockEntityAbstractDrawer holder, PlayerEntity player)
    {
        holder.getWorld().setBlockState(holder.getPos(), holder.getCachedState().with(BorderRegistry.BORDER_TYPE, upgradeTo));
        player.getMainHandStack().decrement(1);
    }

}

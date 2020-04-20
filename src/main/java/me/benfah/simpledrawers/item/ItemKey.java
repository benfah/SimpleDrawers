package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.function.Consumer;

public class ItemKey extends Item implements DrawerInteractable
{

    private Consumer<ItemHolder> action;

    public ItemKey(Settings settings, Consumer<ItemHolder> action)
    {
        super(settings);
        this.action = action;
    }

    public void interact(BlockEntityAbstractDrawer drawer, PlayerEntity player, ItemHolder holder)
    {
        action.accept(holder);
    }

}

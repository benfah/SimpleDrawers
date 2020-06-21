package me.benfah.simpledrawers.block.entity;

import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.AreaHelper;
import me.benfah.simpledrawers.api.drawer.holder.CombinedInventoryHandler;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.init.SDBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityHalfDrawer extends BlockEntityAbstractDrawer implements BlockEntityClientSerializable, Tickable
{

    List<ItemHolder> holderList = new ArrayList<>();
    AreaHelper helper;

    private CombinedInventoryHandler handler;

    public BlockEntityHalfDrawer()
    {
        super(SDBlockEntities.HALF_DRAWER);
        holderList.add(new ItemHolder(16, this));
        holderList.add(new ItemHolder(16, this));
        helper = new AreaHelper(() -> holderList, new AreaHelper.Area(new Vec2f(0, 0), new Vec2f(1F, 0.5F)), new AreaHelper.Area(new Vec2f(0, 0.5F), new Vec2f(1F, 1F)));

        handler = new CombinedInventoryHandler(() -> holderList);
    }

    @Override
    public ItemHolder getItemHolderAt(float x, float y)
    {
        return helper.get(new Vec2f(x, y));
    }

    @Override
    public List<ItemHolder> getItemHolders()
    {
        return holderList;
    }

    @Override
    public void setItemHolders(List<ItemHolder> holders)
    {
        this.holderList.clear();
        this.holderList.addAll(holders);
        handler.generateSlotList();
    }

    @Override
    public CombinedInventoryHandler getInventoryHandler()
    {
        return handler;
    }

}

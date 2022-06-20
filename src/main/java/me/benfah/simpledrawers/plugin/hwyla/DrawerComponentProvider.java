package me.benfah.simpledrawers.plugin.hwyla;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.component.ItemComponent;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;

public class DrawerComponentProvider implements IBlockComponentProvider
{

    public static DrawerComponentProvider INSTANCE = new DrawerComponentProvider();

    DrawerComponentProvider()
    {
    }

    @Override
    public ITooltipComponent getIcon(IBlockAccessor accessor, IPluginConfig config) {
        if(accessor.getBlock() instanceof BlockAbstractDrawer)
        {
            return new ItemComponent(BlockAbstractDrawer.getStack((BlockAbstractDrawer) accessor.getBlock(),
                    accessor.getBlockState().get(BlockAbstractDrawer.BORDER_TYPE)));
        }
        return ItemComponent.EMPTY;
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
    {
        BlockEntityAbstractDrawer drawer = accessor.getBlockEntity();

        ITooltipLine line = tooltip.addLine();

        for(ItemHolder holder : drawer.getItemHolders()) {
            line.with(new ItemComponent(holder.generateStack(holder.getAmount())));
        }
    }

}
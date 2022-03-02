package me.benfah.simpledrawers.plugin.hwyla;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.plugin.vanilla.renderer.Renderers;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class DrawerComponentProvider implements IBlockComponentProvider
{

    public static DrawerComponentProvider INSTANCE = new DrawerComponentProvider();

    DrawerComponentProvider()
    {
    }

    @Override
    public ItemStack getDisplayItem(IBlockAccessor accessor, IPluginConfig config)
    {
        if(accessor.getBlock() instanceof BlockAbstractDrawer)
        {
            return BlockAbstractDrawer.getStack((BlockAbstractDrawer) accessor.getBlock(),
                    accessor.getBlockState().get(BlockAbstractDrawer.BORDER_TYPE));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config)
    {
        BlockEntityAbstractDrawer drawer = accessor.getBlockEntity();

        IDrawableComponent drawable = tooltip.addDrawable();

        for(ItemHolder holder : drawer.getItemHolders()) {
            addItemRenderer(drawable, holder.generateStack(holder.getAmount()));
        }
    }

    private static void addItemRenderer(IDrawableComponent drawable, ItemStack stack)
    {
        NbtCompound tag = new NbtCompound();
        stack.writeNbt(tag);
        drawable.with(Renderers.ITEM, tag);
    }

}
package me.benfah.simpledrawers.plugin.hwyla;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;

public class HwylaPlugin implements IWailaPlugin
{

    @Override
    public void register(IRegistrar registrar)
    {
        registrar.addDisplayItem(DrawerComponentProvider.INSTANCE, BlockAbstractDrawer.class);
        registrar.addComponent(DrawerComponentProvider.INSTANCE, TooltipPosition.BODY, BlockAbstractDrawer.class);
    }

}
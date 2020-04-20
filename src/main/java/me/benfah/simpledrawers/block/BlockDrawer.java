package me.benfah.simpledrawers.block;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.DrawerType;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.init.SDContainers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class BlockDrawer extends BlockAbstractDrawer
{

    public BlockDrawer(Settings settings, Border border)
    {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(BORDER_TYPE, border).with(DRAWER_TYPE,
                DrawerType.FULL));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView arg0)
    {
        return new BlockEntityBasicDrawer();
    }

    @Override
    public Identifier getContainerIdentifier()
    {
        return SDContainers.BASIC_DRAWER_CONTAINER;
    }
}

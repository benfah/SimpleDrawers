package me.benfah.simpledrawers.block;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.DrawerType;
import me.benfah.simpledrawers.block.entity.BlockEntityHalfDrawer;
import me.benfah.simpledrawers.init.SDContainers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class BlockHalfDrawer extends BlockAbstractDrawer
{

	public BlockHalfDrawer(Settings settings, Border border)
	{
		super(settings);
		this.setDefaultState(getDefaultState().with(DrawerType.DRAWER_TYPE, DrawerType.HALF).with(BorderRegistry.BORDER_TYPE, border));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView arg0)
	{
		return new BlockEntityHalfDrawer();
	}

	@Override
	public Identifier getContainerIdentifier()
	{
		return SDContainers.DOUBLE_DRAWER_CONTAINER;
	}

}

package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.models.border.Border;
import me.benfah.simpledrawers.models.border.BorderRegistry;
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
	public void interact(BlockEntityBasicDrawer holder, PlayerEntity player)
	{
		Border currentBorder = holder.getCachedState().get(BorderRegistry.BORDER_TYPE);
		
		if(upgradeTo.compareTo(currentBorder) > 0)
		{
			upgrade(holder, player);
			
		}
	}
	
	public void upgrade(BlockEntityBasicDrawer holder, PlayerEntity player)
	{
		holder.getWorld().setBlockState(holder.getPos(), holder.getCachedState().with(BorderRegistry.BORDER_TYPE, upgradeTo));
		player.getMainHandStack().decrement(1);
	}
	
}

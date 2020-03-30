package me.benfah.simpledrawers.models;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.DrawerType;
import me.benfah.simpledrawers.block.BlockDrawer;
import me.benfah.simpledrawers.block.BlockDrawer.DeserializedInfo;
import me.benfah.simpledrawers.callback.RedirectModelCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DrawerItemModelReplacer implements RedirectModelCallback
{

	@Override
	public BakedModel onRender(ItemStack stack, Mode renderMode, boolean leftHanded, BakedModel model)
	{
		if(stack.getItem() instanceof BlockItem)
		{
			Block block = ((BlockItem)stack.getItem()).getBlock();
			Identifier id = Registry.BLOCK.getId(block);
			if(block instanceof BlockDrawer)
			{
				DeserializedInfo info = BlockDrawer.deserializeInfo(stack);
				Border b = info.getBorder();
				DrawerType type = block.getDefaultState().get(DrawerType.DRAWER_TYPE);
				if(b == null)
				b = block.getDefaultState().get(BorderRegistry.BORDER_TYPE);
				
				return MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(id, "border_type=" + BorderRegistry.getName(b) + ",drawer_type=" + type.asString() + ",facing=north"));
			}
		}
		return model;
	}

}

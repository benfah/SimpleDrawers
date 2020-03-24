package me.benfah.simpledrawers.models;

import java.util.HashMap;
import java.util.Map;

import me.benfah.simpledrawers.block.BlockBasicDrawer;
import me.benfah.simpledrawers.block.BlockBasicDrawer.DeserializedInfo;
import me.benfah.simpledrawers.callback.RedirectModelCallback;
import me.benfah.simpledrawers.models.border.Border;
import me.benfah.simpledrawers.models.border.BorderRegistry;
import me.benfah.simpledrawers.utils.ModelUtils;
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
			if(block instanceof BlockBasicDrawer)
			{
				DeserializedInfo info = BlockBasicDrawer.deserializeInfo(stack);
				Border b = info.getBorder();
				
				if(b == null)
				b = block.getDefaultState().get(BorderRegistry.BORDER_TYPE);
				
				return MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(id, "border_type=" + BorderRegistry.getName(b) + ",facing=north"));
			}
		}
		return model;
	}

}

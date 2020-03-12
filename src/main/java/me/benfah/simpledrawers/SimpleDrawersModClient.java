package me.benfah.simpledrawers;

import me.benfah.simpledrawers.block.entity.renderer.BlockEntityBasicDrawerRenderer;
import me.benfah.simpledrawers.init.SDBlockEntities;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class SimpleDrawersModClient implements ClientModInitializer
{

	@Override
	public void onInitializeClient()
	{
		ModelUtils.loadSpecialModels();
		BlockEntityRendererRegistry.INSTANCE.register(SDBlockEntities.BASIC_DRAWER,
				BlockEntityBasicDrawerRenderer::new);
	}

}

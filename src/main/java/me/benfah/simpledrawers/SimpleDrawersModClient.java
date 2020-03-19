package me.benfah.simpledrawers;

import me.benfah.simpledrawers.block.entity.renderer.BlockEntityBasicDrawerRenderer;
import me.benfah.simpledrawers.callback.ModelPostBakeCallback;
import me.benfah.simpledrawers.callback.ModelPreBakeCallback;
import me.benfah.simpledrawers.init.SDBlockEntities;
import me.benfah.simpledrawers.models.ModelMerger;
import me.benfah.simpledrawers.models.border.BorderLoader;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class SimpleDrawersModClient implements ClientModInitializer
{

	@Override
	public void onInitializeClient()
	{
		ModelPostBakeCallback.EVENT.register(new ModelMerger());
		ModelPreBakeCallback.EVENT.register(new ModelMerger());

		ModelUtils.loadSpecialModels();
		
		ModelLoadingRegistry.INSTANCE.registerAppender(new BorderLoader());
		ModelLoadingRegistry.INSTANCE.registerVariantProvider((resourceManager) -> new BorderLoader());

		BlockEntityRendererRegistry.INSTANCE.register(SDBlockEntities.BASIC_DRAWER,
				BlockEntityBasicDrawerRenderer::new);
	}

}

package me.benfah.simpledrawers;

import me.benfah.simpledrawers.block.entity.renderer.BlockEntityFullDrawerRenderer;
import me.benfah.simpledrawers.block.entity.renderer.BlockEntityHalfDrawerRenderer;
import me.benfah.simpledrawers.block.entity.renderer.BlockEntityQuadDrawerRenderer;
import me.benfah.simpledrawers.callback.ModelPostBakeCallback;
import me.benfah.simpledrawers.callback.ModelPreBakeCallback;
import me.benfah.simpledrawers.callback.RedirectModelCallback;
import me.benfah.simpledrawers.init.SDBlockEntities;
import me.benfah.simpledrawers.init.client.SDContainerScreens;
import me.benfah.simpledrawers.models.ItemModelRedirector;
import me.benfah.simpledrawers.models.ModelMerger;
import me.benfah.simpledrawers.models.border.BorderLoader;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class SimpleDrawersModClient implements ClientModInitializer
{

    @Override
    public void onInitializeClient()
    {
        ModelPostBakeCallback.EVENT.register(new ModelMerger());
        ModelPreBakeCallback.EVENT.register(new ModelMerger());

        RedirectModelCallback.EVENT.register(new ItemModelRedirector());

        ModelUtils.loadSpecialModels();

        ModelLoadingRegistry.INSTANCE.registerModelProvider(new BorderLoader());
        ModelLoadingRegistry.INSTANCE.registerVariantProvider((resourceManager) -> new BorderLoader());

        BlockEntityRendererRegistry.register(SDBlockEntities.BASIC_DRAWER,
                BlockEntityFullDrawerRenderer::new);

        BlockEntityRendererRegistry.register(SDBlockEntities.HALF_DRAWER,
                BlockEntityHalfDrawerRenderer::new);

        BlockEntityRendererRegistry.register(SDBlockEntities.QUAD_DRAWER,
                BlockEntityQuadDrawerRenderer::new);

        SDContainerScreens.initClient();
    }

}

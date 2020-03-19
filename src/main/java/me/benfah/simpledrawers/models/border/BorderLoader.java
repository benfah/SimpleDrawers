package me.benfah.simpledrawers.models.border;

import java.util.function.Consumer;

import me.benfah.simpledrawers.utils.ModelUtils;
import net.fabricmc.fabric.api.client.model.ModelAppender;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;

public class BorderLoader implements ModelAppender, ModelVariantProvider
{

	@Override
	public void appendAll(ResourceManager manager, Consumer<ModelIdentifier> out)
	{
		BorderRegistry.getBorders().forEach(border -> out.accept(new ModelIdentifier(border.getModelIdentifier(), "")));
	}

	@Override
	public UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context)
			throws ModelProviderException
	{
		for (Border b : BorderRegistry.getBorders())
		{
			if (ModelUtils.identifiersEqual(modelId, b.getModelIdentifier()))
			{
				return context.loadModel(b.getModelIdentifier());
			}
		}
		return null;
	}

}

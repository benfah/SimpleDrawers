package me.benfah.simpledrawers.models.border;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.fabricmc.fabric.api.client.model.ModelAppender;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class BorderLoader implements ModelAppender, ModelVariantProvider
{

	@Override
	public void appendAll(ResourceManager manager, Consumer<ModelIdentifier> out)
	{
		BorderRegistry.getBorders().stream().flatMap((border) -> border.getModelMap().values().stream())
				.forEach(modelIdentifier -> out.accept(new ModelIdentifier(modelIdentifier, "")));
	}

	@Override
	public UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context)
			throws ModelProviderException
	{
		for (Identifier id : BorderRegistry.getBorders().stream()
				.flatMap((border) -> border.getModelMap().values().stream()).collect(Collectors.toSet()))
		{
			if (ModelUtils.identifiersEqual(modelId, id))
			{
				return context.loadModel(id);
			}
		}
		return null;
	}

}

package me.benfah.simpledrawers.models.border;

import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.fabricmc.fabric.api.client.model.*;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BorderLoader implements ExtraModelProvider, ModelVariantProvider
{
    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<Identifier> out)
    {
        BorderRegistry.getBorders().stream().flatMap((border) -> border.getModelMap().values().stream())
                .forEach(out);
    }

    @Override
    public UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context)
            throws ModelProviderException
    {
        for(Identifier id : BorderRegistry.getBorders().stream()
                .flatMap((border) -> border.getModelMap().values().stream()).collect(Collectors.toSet()))
        {
            if(ModelUtils.identifiersEqual(modelId, id))
            {
                return context.loadModel(id);
            }
        }
        return null;
    }

}

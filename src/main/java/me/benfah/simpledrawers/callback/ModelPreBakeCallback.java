package me.benfah.simpledrawers.callback;

import java.util.Map;
import java.util.function.BiFunction;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public interface ModelPreBakeCallback
{

	Event<ModelPreBakeCallback> EVENT = EventFactory.createArrayBacked(ModelPreBakeCallback.class,
			(listeners) -> (unbakedModels, bakeFunction, bakedModels) ->
			{
				for (ModelPreBakeCallback event : listeners)
				{
					event.onPreBake(unbakedModels, bakeFunction, bakedModels);
				}
			});

	void onPreBake(Map<Identifier, UnbakedModel> unbaked, BiFunction<Identifier, ModelBakeSettings, BakedModel> bakeFunction,
			Map<ModelIdentifier, BakedModel> baked);

}

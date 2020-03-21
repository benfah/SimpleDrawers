package me.benfah.simpledrawers.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.item.ItemStack;

public interface RedirectModelCallback
{
	Event<RedirectModelCallback> EVENT = EventFactory.createArrayBacked(RedirectModelCallback.class,
			(listeners) -> (stack, renderMode, leftHanded, model) ->
			{
				for (RedirectModelCallback event : listeners)
				{
					return event.onRender(stack, renderMode, leftHanded, model);
				}
				return model;
			});

	BakedModel onRender(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, BakedModel model);
}

package me.benfah.simpledrawers.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface ModelPostBakeCallback
{

    Event<ModelPostBakeCallback> EVENT = EventFactory.createArrayBacked(ModelPostBakeCallback.class,
            (listeners) -> (bakedModels) ->
            {
                for(ModelPostBakeCallback event : listeners)
                {
                    event.onPostBake(bakedModels);
                }
            });

    void onPostBake(Map<Identifier, BakedModel> baked);

}

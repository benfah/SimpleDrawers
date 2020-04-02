package me.benfah.simpledrawers.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.DrawerType;
import me.benfah.simpledrawers.callback.ModelPostBakeCallback;
import me.benfah.simpledrawers.callback.ModelPreBakeCallback;
import me.benfah.simpledrawers.utils.ModelUtils;
import me.benfah.simpledrawers.utils.WrappedBakedModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class ModelMerger implements ModelPostBakeCallback, ModelPreBakeCallback
{

	@Override
	public void onPostBake(Map<ModelIdentifier, BakedModel> baked)
	{
		for (Entry<ModelIdentifier, BakedModel> modelEntry : baked.entrySet())
		{
			if (modelEntry.getKey().toString().contains("border_type"))
			{
				Map<String, String> variantMap = variantToMap(modelEntry.getKey().getVariant());
				if (variantMap.containsKey("border_type"))
				{
					
					String borderType = variantMap.get("border_type");
					Border border = BorderRegistry.getBorder(borderType);
					
					Identifier borderIdentifier = border.getModelMap().get(DrawerType.DRAWER_TYPE.parse(variantMap.get("drawer_type")).orElse(DrawerType.FULL));
					
					BakedModel borderModel = baked.get(
							new ModelIdentifier(borderIdentifier, "facing=" + variantMap.get("facing")));
					baked.put(modelEntry.getKey(), new WrappedBakedModel(modelEntry.getValue(), borderModel));
				}
			}
		}
	}

	@Override
	public void onPreBake(Map<Identifier, UnbakedModel> unbaked,
			BiFunction<Identifier, ModelBakeSettings, BakedModel> bakeFunction, Map<ModelIdentifier, BakedModel> baked)
	{
		List<Identifier> toBake = BorderRegistry.getBorders().stream()
				.flatMap((border) -> border.getModelMap().values().stream()).collect(Collectors.toList());

		for (Identifier borderIdentifier : toBake)
		{
			baked.put(new ModelIdentifier(borderIdentifier, "facing=north"),
					bakeFunction.apply(borderIdentifier, ModelRotation.X0_Y0));
			baked.put(new ModelIdentifier(borderIdentifier, "facing=east"),
					bakeFunction.apply(borderIdentifier, ModelRotation.X0_Y90));
			baked.put(new ModelIdentifier(borderIdentifier, "facing=south"),
					bakeFunction.apply(borderIdentifier, ModelRotation.X0_Y180));
			baked.put(new ModelIdentifier(borderIdentifier, "facing=west"),
					bakeFunction.apply(borderIdentifier, ModelRotation.X0_Y270));

			new HashSet<>(unbaked.keySet()).stream()
					.filter((identifier) -> ModelUtils.identifiersEqual(identifier, borderIdentifier))
					.forEach(identifier -> unbaked.remove(identifier));
		}

	}

	public static Map<String, String> variantToMap(String variant)
	{
		HashMap<String, String> resultMap = new HashMap<>();
		String[] properties = variant.split(",");

		for (String property : properties)
		{
			if (!property.isEmpty())
			{
				String[] keyValue = property.split("=");
				resultMap.put(keyValue[0], keyValue[1]);
			}
		}
		return resultMap;
	}

	public static String mapToVariant(Map<String, String> map)
	{
		List<Entry<String, String>> entryList = new ArrayList<>(map.entrySet());
		Iterator<Entry<String, String>> it = entryList.iterator();

		String result = "";

		while (it.hasNext())
		{
			Entry<String, String> entry = it.next();
			result = result + entry.getKey() + "=" + entry.getValue();
			if (it.hasNext())
				result = result + ",";
		}
		return result;
	}

}

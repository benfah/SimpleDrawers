package me.benfah.simpledrawers.models.border;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.state.property.AbstractProperty;

public class BorderRegistry
{
	private static BiMap<String, Border> BORDER_MAP = HashBiMap.create();
	
	public static BorderProperty BORDER_TYPE = new BorderProperty("border_type", Border.class);
	
	public static void register(String name, Border border)
	{
		BORDER_MAP.put(name, border);
	}
	
	public static Border getBorder(String name)
	{
		return BORDER_MAP.get(name);
	}
	
	public static String getName(Border border)
	{
		return BORDER_MAP.inverse().get(border);
	}
	
	public static Set<Border> getBorders()
	{
		return BORDER_MAP.values();
	}
	
	public static class BorderProperty extends AbstractProperty<Border>
	{

		protected BorderProperty(String name, Class<Border> type)
		{
			super(name, type);
		}

		@Override
		public Collection<Border> getValues()
		{
			return BORDER_MAP.values();
		}

		@Override
		public Optional<Border> parse(String name)
		{
			if(BORDER_MAP.containsKey(name))
			return Optional.of(BORDER_MAP.get(name));
			return Optional.empty();
		}

		@Override
		public String name(Border value)
		{
			return BORDER_MAP.inverse().get(value);
		}
		
	}
}

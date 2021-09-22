package me.benfah.simpledrawers.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;

import java.util.HashMap;
import java.util.Map;

public class PropertyStateConverter
{

    private PropertyStateConverter()
    {

    }

    BlockState state;
    String propertyString;
    ;

    public static <T extends Comparable<T>> BlockState fromPropertyString(Block block, String propertyString)
    {
        Map<String, String> map = parsePropertyString(propertyString);
        Map<Property<?>, Comparable<?>> propertyMap = new HashMap<>();

        BlockState result = block.getDefaultState();

        for(Map.Entry<String, String> entry : map.entrySet())
        {
            Property<T> property = (Property<T>) block.getStateManager().getProperty(entry.getKey());
            T value = property.parse(entry.getValue()).get();
            result = result.with(property, value);
        }
        return result;
    }

    public static <T extends Comparable<T>> BlockState fromPropertyMap(Block block, Map<Property<T>, Comparable<T>> propertyMap)
    {
        BlockState result = block.getDefaultState();

        for(Map.Entry<Property<T>, Comparable<T>> entry : propertyMap.entrySet())
        {
            result = result.with(entry.getKey(), (T) entry.getValue());
        }
        return result;
    }

    private static Map<String, String> parsePropertyString(String propertyString)
    {
        Map<String, String> result = new HashMap<>();
        for(String s : propertyString.split(","))
        {
            String[] keyValuePair = s.split("=");
            result.put(keyValuePair[0], keyValuePair[1]);
        }
        return result;
    }



}

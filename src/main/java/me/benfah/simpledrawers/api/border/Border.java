package me.benfah.simpledrawers.api.border;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.api.drawer.DrawerType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class Border implements Comparable<Border>
{
	/**
	 * Prefix for a full border is "block/border/<border name>", and for a half
	 * border "block/half_border/<border name>". Both have to be implemented.
	 */
	private static final String BORDER_LOCATION_PREFIX = "block/";

	private int stackMultiplier;
	
	private Map<DrawerType, Identifier> identifierMap = new HashMap<>();
	
	BorderType borderType;

	public Border(int stackMultiplier, String modid, String name, BorderType type)
	{
		this.stackMultiplier = stackMultiplier;
		initIdentifierMap(modid, name);
		this.borderType = type;
	}
	
	private void initIdentifierMap(String modid, String name)
	{
		for(DrawerType type : DrawerType.values())
		{
			identifierMap.put(type, new Identifier(modid, BORDER_LOCATION_PREFIX + type.getAssetsPath() + "/" + name));
		}
	}
	
	public int getStackMultiplier()
	{
		return stackMultiplier;
	}
	
	public Map<DrawerType, Identifier> getModelMap()
	{
		return identifierMap;
	}
	
	public BorderType getBorderType()
	{
		return borderType;
	}

	@Override
	public int compareTo(Border o)
	{
		if (o.equals(this))
			return 0;

		if (getStackMultiplier() < o.getStackMultiplier())
			return -1;

		return 1;
	}

	public static Border OAK_BORDER = new Border(1, SimpleDrawersMod.MOD_ID, "oak_border", BorderType.BASIC);
	public static Border BIRCH_BORDER = new Border(1, SimpleDrawersMod.MOD_ID, "birch_border", BorderType.BASIC);
	public static Border DARK_OAK_BORDER = new Border(1, SimpleDrawersMod.MOD_ID, "dark_oak_border", BorderType.BASIC);
	public static Border SPRUCE_BORDER = new Border(1, SimpleDrawersMod.MOD_ID, "spruce_border", BorderType.BASIC);
	public static Border ACACIA_BORDER = new Border(1, SimpleDrawersMod.MOD_ID, "acacia_border", BorderType.BASIC);
	public static Border JUNGLE_BORDER = new Border(1, SimpleDrawersMod.MOD_ID, "jungle_border", BorderType.BASIC);

	public static Border IRON_BORDER = new Border(2, SimpleDrawersMod.MOD_ID, "iron_border", BorderType.UPGRADED);
	public static Border GOLD_BORDER = new Border(4, SimpleDrawersMod.MOD_ID, "gold_border", BorderType.UPGRADED);
	public static Border DIAMOND_BORDER = new Border(8, SimpleDrawersMod.MOD_ID, "diamond_border", BorderType.UPGRADED);
	public static Border EMERALD_BORDER = new Border(16, SimpleDrawersMod.MOD_ID, "emerald_border",
			BorderType.UPGRADED);

	public static void init()
	{
		BorderRegistry.register("oak_border", OAK_BORDER);
		BorderRegistry.register("birch_border", BIRCH_BORDER);
		BorderRegistry.register("dark_oak_border", DARK_OAK_BORDER);
		BorderRegistry.register("spruce_border", SPRUCE_BORDER);
		BorderRegistry.register("acacia_border", ACACIA_BORDER);
		BorderRegistry.register("jungle_border", JUNGLE_BORDER);

		BorderRegistry.register("iron_border", IRON_BORDER);
		BorderRegistry.register("gold_border", GOLD_BORDER);
		BorderRegistry.register("diamond_border", DIAMOND_BORDER);
		BorderRegistry.register("emerald_border", EMERALD_BORDER);

	}

	public enum BorderType
	{
		BASIC, UPGRADED;
	}

}

package me.benfah.simpledrawers.models.border;

import java.util.function.Supplier;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.init.SDItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Border implements Comparable<Border>
{

	private int stackMultiplier;
	private Identifier identifier;

	BorderType borderType;

	Supplier<Item> drop;

	public Border(int stackMultiplier, Identifier identifier, BorderType type, Supplier<Item> asDrop)
	{
		this.stackMultiplier = stackMultiplier;
		this.identifier = identifier;
		this.borderType = type;
		this.drop = asDrop;
	}

	public int getStackMultiplier()
	{
		return stackMultiplier;
	}

	public ItemStack getDropItem()
	{
		if (drop != null)
			return new ItemStack(drop.get());

		return null;
	}

	public Identifier getModelIdentifier()
	{
		return identifier;
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

	public static Border OAK_BORDER = new Border(1, new Identifier(SimpleDrawersMod.MOD_ID, "block/border/oak_border"),
			BorderType.BASIC, null);
	public static Border BIRCH_BORDER = new Border(1,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/birch_border"), BorderType.BASIC, null);
	public static Border DARK_OAK_BORDER = new Border(1,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/dark_oak_border"), BorderType.BASIC, null);
	public static Border SPRUCE_BORDER = new Border(1,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/spruce_border"), BorderType.BASIC, null);
	public static Border ACACIA_BORDER = new Border(1,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/acacia_border"), BorderType.BASIC, null);
	public static Border JUNGLE_BORDER = new Border(1,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/jungle_border"), BorderType.BASIC, null);

	public static Border IRON_BORDER = new Border(2,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/iron_border"), BorderType.UPGRADED,
			() -> SDItems.BASIC_IRON_UPGRADE);
	public static Border GOLD_BORDER = new Border(4,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/gold_border"), BorderType.UPGRADED,
			() -> SDItems.FULL_GOLD_UPGRADE);
	public static Border DIAMOND_BORDER = new Border(8,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/diamond_border"), BorderType.UPGRADED,
			() -> SDItems.FULL_DIAMOND_UPGRADE);
	public static Border EMERALD_BORDER = new Border(16,
			new Identifier(SimpleDrawersMod.MOD_ID, "block/border/emerald_border"), BorderType.UPGRADED,
			() -> SDItems.FULL_EMERALD_UPGRADE);

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

	public static enum BorderType
	{
		BASIC, UPGRADED;
	}

}

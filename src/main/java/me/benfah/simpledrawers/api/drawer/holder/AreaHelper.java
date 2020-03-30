package me.benfah.simpledrawers.api.drawer.holder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import net.minecraft.util.math.Vec2f;

public class AreaHelper
{
	Supplier<List<ItemHolder>> holderSupplier;

	List<Area> areaList = new ArrayList<>();

	public AreaHelper(Supplier<List<ItemHolder>> holderSupplier, Area... area)
	{
		this.holderSupplier = holderSupplier;
		areaList.addAll(Arrays.asList(area));
	}

	public void add(Vec2f from, Vec2f to)
	{
		areaList.add(new Area(from, to));
	}

	public int getIndex(Vec2f point)
	{
		return IntStream.range(0, areaList.size()).filter((index) -> areaList.get(index).isInArea(point)).findFirst()
				.getAsInt();
	}

	public ItemHolder get(Vec2f point)
	{
		return holderSupplier.get().get(getIndex(point));
	}

	public static class Area
	{
		Vec2f from;
		Vec2f to;

		public Area(Vec2f from, Vec2f to)
		{
			this.from = from;
			this.to = to;
		}

		private boolean isInArea(Vec2f vec)
		{
			return from.x <= vec.x && from.y <= vec.y && to.x >= vec.x && to.y >= vec.y;
		}

	}

}

package me.benfah.simpledrawers.utils;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlockUtils
{

    public static final Map<Direction, Function<BlockHitResult, Float>> DIRECTION_MAP = new HashMap<>();

    public static Vec2f getCoordinatesFromHitResult(BlockHitResult result)
    {
        float newY = 1F - (float) (result.getPos().y - result.getBlockPos().getY());
        float newX = DIRECTION_MAP.get(result.getSide()).apply(result);

        return new Vec2f(newX, newY);
    }

    static
    {
        DIRECTION_MAP.put(Direction.NORTH, (result) -> 1F - (float) (result.getPos().getX() - result.getBlockPos().getX()));
        DIRECTION_MAP.put(Direction.EAST, (result) -> 1F - (float) (result.getPos().getZ() - result.getBlockPos().getZ()));
        DIRECTION_MAP.put(Direction.SOUTH, (result) -> (float) (result.getPos().getX() - result.getBlockPos().getX()));
        DIRECTION_MAP.put(Direction.WEST, (result) -> (float) (result.getPos().getZ() - result.getBlockPos().getZ()));

    }

}

package me.benfah.simpledrawers.utils;

import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3f;

public class RenderConstants
{
    public static final Vec3f DIFFUSE_LIGHT_0 = Util.make(new Vec3f(0.2F, 1.0F, -0.7F), Vec3f::normalize);
    public static final Vec3f DIFFUSE_LIGHT_1 = Util.make(new Vec3f(-0.2F, 1.0F, 0.7F), Vec3f::normalize);

}

package me.benfah.simpledrawers.utils;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Util;

public class RenderConstants
{
    public static final Vector3f DIFFUSE_LIGHT_0 = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
    public static final Vector3f DIFFUSE_LIGHT_1 = Util.make(new Vector3f(-0.2F, 1.0F, 0.7F), Vector3f::normalize);

}

package me.benfah.simpledrawers.init.client;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.container.client.BasicDrawerContainerScreen;
import me.benfah.simpledrawers.init.SDContainers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SDContainerScreens
{
    public static void initClient()
    {
        ScreenProviderRegistry.INSTANCE.registerFactory(SDContainers.BASIC_DRAWER_CONTAINER, (container) -> new BasicDrawerContainerScreen((DrawerContainer<?>) container, new Identifier(SimpleDrawersMod.MOD_ID, "textures/gui/drawer_1.png")));
        ScreenProviderRegistry.INSTANCE.registerFactory(SDContainers.DOUBLE_DRAWER_CONTAINER, (container) -> new BasicDrawerContainerScreen((DrawerContainer<?>) container, new Identifier(SimpleDrawersMod.MOD_ID, "textures/gui/drawer_2.png")));
        ScreenProviderRegistry.INSTANCE.registerFactory(SDContainers.QUAD_DRAWER_CONTAINER, (container) -> new BasicDrawerContainerScreen((DrawerContainer<?>) container, new Identifier(SimpleDrawersMod.MOD_ID, "textures/gui/drawer_4.png")));

    }
}

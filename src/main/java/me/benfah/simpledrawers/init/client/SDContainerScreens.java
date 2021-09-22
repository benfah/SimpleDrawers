package me.benfah.simpledrawers.init.client;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.container.client.BasicDrawerContainerScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static me.benfah.simpledrawers.init.SDContainers.*;

@Environment(EnvType.CLIENT)
public class SDContainerScreens
{
    public static void initClient()
    {
        ScreenRegistry.register(BASIC_DRAWER_CONTAINER_TYPE, (DrawerContainer handler, PlayerInventory inventory, Text title) ->
            new BasicDrawerContainerScreen<>(handler, title, new Identifier(SimpleDrawersMod.MOD_ID, "textures/gui/drawer_1.png")));

        ScreenRegistry.register(DOUBLE_DRAWER_CONTAINER_TYPE, (DrawerContainer handler, PlayerInventory inventory, Text title) ->
            new BasicDrawerContainerScreen<>(handler, title, new Identifier(SimpleDrawersMod.MOD_ID, "textures/gui/drawer_2.png")));

        ScreenRegistry.register(QUAD_DRAWER_CONTAINER_TYPE, (DrawerContainer handler, PlayerInventory inventory, Text title) ->
            new BasicDrawerContainerScreen<>(handler, title, new Identifier(SimpleDrawersMod.MOD_ID, "textures/gui/drawer_4.png")));
    }
}

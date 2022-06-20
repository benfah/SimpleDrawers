package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SDContainers
{

    public static Identifier BASIC_DRAWER_CONTAINER = new Identifier(SimpleDrawersMod.MOD_ID, "basic_drawer");
    public static Identifier DOUBLE_DRAWER_CONTAINER = new Identifier(SimpleDrawersMod.MOD_ID, "double_drawer");
    public static Identifier QUAD_DRAWER_CONTAINER = new Identifier(SimpleDrawersMod.MOD_ID, "quad_drawer");

    public static ScreenHandlerType<DrawerContainer> BASIC_DRAWER_CONTAINER_TYPE;
    public static ScreenHandlerType<DrawerContainer> DOUBLE_DRAWER_CONTAINER_TYPE;
    public static ScreenHandlerType<DrawerContainer> QUAD_DRAWER_CONTAINER_TYPE;

    public SDContainers()
    {
    }

    private static DrawerContainer createContainer(ScreenHandlerType<DrawerContainer> type,
            int syncId, PlayerInventory inventory, PacketByteBuf buf)
    {
        BlockPos blockPos = buf.readBlockPos();

        BlockEntity blockEntity = inventory.player.world.getBlockEntity(blockPos);

        return new DrawerContainer(type, syncId, inventory, (BlockEntityAbstractDrawer) blockEntity);
    }

    public static void init()
    {
        BASIC_DRAWER_CONTAINER_TYPE = ScreenHandlerRegistry.registerExtended(BASIC_DRAWER_CONTAINER, (syncId, inventory, buf) ->
            createContainer(BASIC_DRAWER_CONTAINER_TYPE, syncId, inventory, buf));

        DOUBLE_DRAWER_CONTAINER_TYPE = ScreenHandlerRegistry.registerExtended(DOUBLE_DRAWER_CONTAINER, (syncId, inventory, buf) ->
            createContainer(DOUBLE_DRAWER_CONTAINER_TYPE, syncId, inventory, buf));

        QUAD_DRAWER_CONTAINER_TYPE = ScreenHandlerRegistry.registerExtended(QUAD_DRAWER_CONTAINER, (syncId, inventory, buf) ->
            createContainer(QUAD_DRAWER_CONTAINER_TYPE, syncId, inventory, buf));
    }
}

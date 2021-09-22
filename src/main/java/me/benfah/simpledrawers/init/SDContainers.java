package me.benfah.simpledrawers.init;

import me.benfah.simpledrawers.SimpleDrawersMod;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityHalfDrawer;
import me.benfah.simpledrawers.block.entity.BlockEntityQuadDrawer;
import me.benfah.simpledrawers.container.BasicDrawerContainer;
import me.benfah.simpledrawers.container.DoubleDrawerContainer;
import me.benfah.simpledrawers.container.QuadDrawerContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SDContainers
{

    public static Identifier BASIC_DRAWER_CONTAINER = new Identifier(SimpleDrawersMod.MOD_ID, "basic_drawer");
    public static Identifier DOUBLE_DRAWER_CONTAINER = new Identifier(SimpleDrawersMod.MOD_ID, "double_drawer");
    public static Identifier QUAD_DRAWER_CONTAINER = new Identifier(SimpleDrawersMod.MOD_ID, "quad_drawer");

    public SDContainers()
    {
    }

    public static void init()
    {
        // TODO use ScreenHandlerRegistry
        ContainerProviderRegistry.INSTANCE.registerFactory(BASIC_DRAWER_CONTAINER, (syncId, identifier, player, buf) ->
        {
            BlockPos blockPos = buf.readBlockPos();

            BlockEntityAbstractDrawer blockEntity = (BlockEntityAbstractDrawer) player.world.getBlockEntity(blockPos);

            return new BasicDrawerContainer(syncId, player, (BlockEntityBasicDrawer) blockEntity);
        });

        ContainerProviderRegistry.INSTANCE.registerFactory(DOUBLE_DRAWER_CONTAINER, (syncId, identifier, player, buf) ->
        {
            BlockPos blockPos = buf.readBlockPos();

            BlockEntityAbstractDrawer blockEntity = (BlockEntityAbstractDrawer) player.world.getBlockEntity(blockPos);

            return new DoubleDrawerContainer(syncId, player, (BlockEntityHalfDrawer) blockEntity);
        });

        ContainerProviderRegistry.INSTANCE.registerFactory(QUAD_DRAWER_CONTAINER, (syncId, identifier, player, buf) ->
        {
            BlockPos blockPos = buf.readBlockPos();

            BlockEntityAbstractDrawer blockEntity = (BlockEntityAbstractDrawer) player.world.getBlockEntity(blockPos);

            return new QuadDrawerContainer(syncId, player, (BlockEntityQuadDrawer) blockEntity);
        });
    }



}

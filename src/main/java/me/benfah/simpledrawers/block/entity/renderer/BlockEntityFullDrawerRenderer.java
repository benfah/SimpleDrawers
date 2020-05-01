package me.benfah.simpledrawers.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.block.entity.BlockEntityBasicDrawer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import static me.benfah.simpledrawers.utils.RenderConstants.*;

public class BlockEntityFullDrawerRenderer extends BlockEntityAbstractDrawerRenderer<BlockEntityBasicDrawer>
{

    public BlockEntityFullDrawerRenderer(BlockEntityRenderDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(BlockEntityBasicDrawer blockEntity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        light = calcLight(blockEntity);


        Direction facing = blockEntity.getCachedState().get(BlockAbstractDrawer.FACING);
        ItemHolder holder = blockEntity.getItemHolderAt(0, 0);
        if(holder.isLocked())
            drawLock(8, 13.5, matrices, vertexConsumers, light, overlay, facing);


        if(!holder.isEmpty() || holder.isLocked())
        {
            drawCenteredText(8, 11, holder.getDisplayAmount(), matrices, vertexConsumers, facing);
            drawItem(8, 6, 0.4F, holder.generateStack(1), matrices, vertexConsumers, light, overlay, facing);


            matrices.pop();
            RenderSystem.setupLevelDiffuseLighting(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1, matrices.peek().getModel());
            matrices.push();
        }


    }

}

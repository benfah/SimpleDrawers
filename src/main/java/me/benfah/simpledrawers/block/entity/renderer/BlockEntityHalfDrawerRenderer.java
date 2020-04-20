package me.benfah.simpledrawers.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.block.entity.BlockEntityHalfDrawer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

public class BlockEntityHalfDrawerRenderer extends BlockEntityAbstractDrawerRenderer<BlockEntityHalfDrawer>
{

    public BlockEntityHalfDrawerRenderer(BlockEntityRenderDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(BlockEntityHalfDrawer blockEntity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        light = calcLight(blockEntity);

        Direction facing = blockEntity.getCachedState().get(BlockAbstractDrawer.FACING);
        float offset = 0;

        for(ItemHolder holder : blockEntity.getItemHolders())
        {
            drawHolder(offset, holder, matrices, vertexConsumers, light, overlay, facing);
            offset += 7.5;
        }

        matrices.pop();
        RenderSystem.setupLevelDiffuseLighting(matrices.peek().getModel());
        matrices.push();

    }

    private void drawHolder(float offsetY, ItemHolder holder, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                            int overlay, Direction facing)
    {
        if(holder.isLocked())
            drawLock(16 - 2.5, 16 - 5 - offsetY, matrices, vertexConsumers, light, overlay, facing);

        if(!holder.isEmpty() || holder.isLocked())
        {
            drawCenteredText(11.5, 3.5 + offsetY, holder.getDisplayAmount(), matrices, vertexConsumers, facing);
            drawItem(6, 4.25 + offsetY, 0.3F, holder.generateStack(1), matrices, vertexConsumers, light, overlay, facing);


        }
    }

}

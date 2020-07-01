package me.benfah.simpledrawers.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.world.LightType;
import static me.benfah.simpledrawers.utils.RenderConstants.*;

public abstract class BlockEntityAbstractDrawerRenderer<B extends BlockEntityAbstractDrawer> extends BlockEntityRenderer<B>
{



    public BlockEntityAbstractDrawerRenderer(BlockEntityRenderDispatcher dispatcher)
    {
        super(dispatcher);
    }


    public void transformToFace(MatrixStack stack, Direction d)
    {
        stack.translate(.5f, .5f, .5f);
        stack.multiply(d.getRotationQuaternion());
        stack.multiply(new Quaternion(Vector3f.POSITIVE_X, 90, true));
        stack.translate(-.5f, -.5f, -.5f);
    }

    public void transformAttribToFace(MatrixStack stack, Direction d)
    {
        stack.translate(.5f, .5f, .5f);
        stack.multiply(d.getRotationQuaternion());
        stack.multiply(new Quaternion(Vector3f.NEGATIVE_X, 90, true));
        stack.translate(-.5f, -.5f, -.5f);
    }

    // render method gives a light argument with 0, so i have to get it somewhere else
    protected int calcLight(BlockEntityAbstractDrawer blockEntity)
    {
        Direction d = blockEntity.getCachedState().get(BlockAbstractDrawer.FACING);
        BlockPos pos = blockEntity.getPos().add(d.getVector());
        int skyLight = blockEntity.getWorld().getLightLevel(LightType.SKY, pos);
        int blockLight = blockEntity.getWorld().getLightLevel(LightType.BLOCK, pos);

        return skyLight << 20 | blockLight << 4;
    }


    public void drawLock(double x, double y, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Direction facing)
    {
        matrices.push();
        transformAttribToFace(matrices, facing.getOpposite());


        transformToPosition(x, y, matrices);
        matrices.scale(1 / 16f, 1 / 16f, 1 / 16f);
        matrices.translate(-0.5, 0, 0);

        ModelUtils.drawSpecialTexture(matrices, vertexConsumers, ModelUtils.SpecialModel.LOCK.getBakedModel(), light, overlay);

        matrices.pop();
    }

    public void drawItem(double x, double y, float scale, ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Direction facing)
    {
        matrices.push();
//		matrices.translate(0, 1, 0);
        transformToFace(matrices, facing);
        transformToPosition(x, y, matrices);
        matrices.translate(0, 0, -0.01);
        matrices.multiply(new Quaternion(Vector3f.NEGATIVE_Z, 180, true));
        matrices.multiply(new Quaternion(Vector3f.NEGATIVE_Y, 180, true));
        matrices.scale(scale, scale, 0.0001f);
        if(vertexConsumers instanceof VertexConsumerProvider.Immediate)
            ((VertexConsumerProvider.Immediate) vertexConsumers).draw();

        BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(stack);
        if(model.hasDepth())
            RenderSystem.setupGui3DDiffuseLighting(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1);
        else
            RenderSystem.setupGuiFlatDiffuseLighting(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1);
        matrices.peek().getNormal().load(Matrix3f.scale(1, -1, 1));
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, Mode.GUI, light, overlay, matrices, vertexConsumers);

        if(vertexConsumers instanceof VertexConsumerProvider.Immediate)
            ((VertexConsumerProvider.Immediate) vertexConsumers).draw();
        matrices.pop();
    }

    public void drawCenteredText(double x, double y, String s, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Direction facing)
    {
        matrices.push();

        transformToFace(matrices, facing);
        transformToPosition(x, y, matrices);

        matrices.scale(0.01f, 0.01f, 0.01f);

        int width = dispatcher.getTextRenderer().getWidth(new LiteralText(s));

        dispatcher.getTextRenderer().draw(s, -width / 2, 3, 0, false, matrices.peek().getModel(), vertexConsumers, false, 0, 15728880);

        matrices.pop();
    }

    public void transformToPosition(double x, double y, MatrixStack matrices)
    {
        matrices.translate(x / 16d, y / 16d, 0.99d / 16d);
    }

    public void transformToCenteredPosition(double y, MatrixStack matrices)
    {
        transformToPosition(8d, y, matrices);
    }


}

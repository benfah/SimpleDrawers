package me.benfah.simpledrawers.models;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer.DeserializedInfo;
import me.benfah.simpledrawers.callback.RedirectModelCallback;
import me.benfah.simpledrawers.item.ItemPackagedDrawer;
import me.benfah.simpledrawers.utils.ModelUtils;
import me.benfah.simpledrawers.utils.PropertyStateConverter;
import me.benfah.simpledrawers.utils.WrappedBakedModel;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

public class ItemModelRedirector implements RedirectModelCallback
{

    @Override
    public BakedModel onRender(ItemStack stack, Mode renderMode, boolean leftHanded, BakedModel model)
    {
        if(stack.getItem() instanceof BlockItem)
        {
            Block block = ((BlockItem) stack.getItem()).getBlock();
            if(block instanceof BlockAbstractDrawer)
            {
                DeserializedInfo info = BlockAbstractDrawer.deserializeInfo(stack);
                Border b = info.getBorder();
                if(b == null)
                    b = block.getDefaultState().get(BorderRegistry.BORDER_TYPE);

                return ModelUtils.getBakedDrawerModel(block.getDefaultState().with(BorderRegistry.BORDER_TYPE, b));
            }
        }
        if(stack.getItem() instanceof ItemPackagedDrawer)
        {
            CompoundTag tag = stack.getSubTag("DrawerInfo");
            if(tag != null)
            {
                BlockAbstractDrawer drawer = (BlockAbstractDrawer) Registry.BLOCK.get(new Identifier(tag.getString("Id")));


                Border border = PropertyStateConverter.fromPropertyString(drawer, tag.getString("Variant")).get(BlockAbstractDrawer.BORDER_TYPE);

                return new WrappedBakedModel(ModelUtils.getBakedDrawerModel(drawer.getDefaultState().with(BorderRegistry.BORDER_TYPE, border)), model);

//                return MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(Registry.ITEM.getId(stack.getItem()), tag.getString("DrawerId")));
            }

        }

        return model;
    }

}

package me.benfah.simpledrawers.models;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer.DeserializedInfo;
import me.benfah.simpledrawers.callback.RedirectModelCallback;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class DrawerItemModelReplacer implements RedirectModelCallback
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
        return model;
    }

}

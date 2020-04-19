package me.benfah.simpledrawers.container.client;

import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.container.client.DrawerContainerScreen;
import me.benfah.simpledrawers.container.BasicDrawerContainer;
import me.benfah.simpledrawers.utils.ModelUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class BasicDrawerContainerScreen<T extends DrawerContainer<?>> extends DrawerContainerScreen<T>
{

    private Identifier texture;

    public BasicDrawerContainerScreen(T container, Identifier texture)
    {
        super(container);
        this.texture = texture;
    }

    @Override
    public Identifier getBackgroundTexture()
    {
        return texture;
    }
}

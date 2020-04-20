package me.benfah.simpledrawers.container.client;

import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.container.client.DrawerContainerScreen;
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

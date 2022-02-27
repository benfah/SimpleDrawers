package me.benfah.simpledrawers.container.client;

import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.container.client.DrawerContainerScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BasicDrawerContainerScreen<T extends DrawerContainer> extends DrawerContainerScreen<T>
{

    private Identifier texture;

    public BasicDrawerContainerScreen(T container, Text title, Identifier texture)
    {
        super(container, title);
        this.texture = texture;
    }

    @Override
    public Identifier getBackgroundTexture()
    {
        return texture;
    }
}

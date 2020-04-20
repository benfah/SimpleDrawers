package me.benfah.simpledrawers.api.container.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.drawer.holder.HolderSlot;
import me.benfah.simpledrawers.utils.NumberUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class DrawerContainerScreen<T extends DrawerContainer<?>> extends ContainerScreen<T>
{

    public DrawerContainerScreen(T container)
    {

        super(container, container.playerInv, container.drawer.getCachedState().getBlock().asItem().getName());

        this.containerWidth = 176;
        this.containerHeight = 187;
    }

    protected void drawForeground(int mouseX, int mouseY)
    {
        container.holderSlots.forEach((slot) ->
        {
            itemRenderer.renderGuiItem(slot.getStack(), slot.xPosition, slot.yPosition);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(slot.xPosition + 3, slot.yPosition + 3, 0);
            RenderSystem.scalef(0.75f, 0.75f, 1);
            itemRenderer.renderGuiItemOverlay(font, slot.getStack(), 0, 0, NumberUtils.displayShortNumber(slot.holder.get().getAmount()));
            RenderSystem.popMatrix();

        });
        HolderSlot slot = getHolderSlotAt(mouseX, mouseY);
        if(slot != null)
        {
            int slotX = slot.xPosition;
            int slotY = slot.yPosition;

            RenderSystem.disableDepthTest();
            RenderSystem.colorMask(true, true, true, false);
            this.fillGradient(slotX, slotY, slotX + 16, slotY + 16, -2130706433, -2130706433);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
        }
        this.font.draw(this.title.asFormattedString(), 8.0F, 6.0F, 4210752);
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F,
                (float) (this.containerHeight - 96 + 2), 4210752);

    }

    public HolderSlot getHolderSlotAt(int x, int y)
    {
        return container.holderSlots.stream()
                .filter((slot) -> isPointWithinBounds(slot.xPosition, slot.yPosition, 16, 16, x, y)).findAny()
                .orElse(null);
    }

    protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX,
                                          double pointY)
    {
        int i = this.x;
        int j = this.y;
        pointX -= (double) i;
        pointY -= (double) j;
        return pointX >= (double) (xPosition - 1) && pointX < (double) (xPosition + width + 1)
                && pointY >= (double) (yPosition - 1) && pointY < (double) (yPosition + height + 1);
    }

    public void render(int mouseX, int mouseY, float delta)
    {
        this.renderBackground();
        this.drawBackground(delta, mouseX, mouseY);
        super.render(mouseX, mouseY, delta);
        HolderSlot slot = getHolderSlotAt(mouseX, mouseY);

        if(slot != null)
        {
            focusedSlot = slot;
        }

        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    protected void drawBackground(float delta, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(getBackgroundTexture());
        int i = (this.width - this.containerWidth) / 2;
        int j = (this.height - this.containerHeight) / 2;
        this.blit(i, j, 0, 0, this.containerWidth, this.containerHeight);

    }

    public abstract Identifier getBackgroundTexture();

}

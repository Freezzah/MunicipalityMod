package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.municipality.Municipality;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class InhabitantsScreen extends Screen {
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Constants.MOD_ID, "textures/gui/screen/townhall_screen_background.png");
    private final Municipality municipality;
    private final int verticalSpacing = 20;
    private final int horizontalSpacing = 20;
    private final int offsetBottom = this.height;
    private int imageWidth;
    private int imageHeight;

    public InhabitantsScreen(Component title, Municipality municipality) {
        super(title);
        this.municipality = municipality;
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 256;
        this.imageHeight = 256;
    }


    @Override
    public void renderBackground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
        graphics.blit(BACKGROUND_LOCATION, ((this.width - this.imageWidth) / 2), ((this.height - this.imageHeight) / 2), 0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
    }


    @Override
    public void renderTransparentBackground(@NotNull GuiGraphics graphics) {

    }
}
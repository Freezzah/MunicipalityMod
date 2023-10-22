package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.menu.TownhallMenu;
import com.freezzah.municipality.municipality.IMunicipality;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TownhallScreen extends MunicipalityScreen<TownhallMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Constants.MOD_ID, "textures/gui/screen/townhall_screen_background.png");
    @SuppressWarnings("FieldCanBeLocal")
    private final int startOffsetTopEach = 20;
    private final IMunicipality municipality;
    private int startOffsetTop = this.topPos + 30; //DYNAMIC
    private int startOffsetLeft = this.leftPos + 20;
    @SuppressWarnings("FieldCanBeLocal")
    private int startOffsetLeftMiddle = this.imageWidth / 2 + startOffsetLeft;

    public TownhallScreen(TownhallMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.municipality = menu.getMunicipality();
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.width = 480;
        this.height = 253;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        startOffsetLeft = this.leftPos + 20;
        startOffsetLeftMiddle = this.imageWidth / 2 + startOffsetLeft;
        graphics.drawString(this.font, Localization.TEXT_MUNICIPALITY_NAME, startOffsetLeft, startOffsetTop, 4210752);
        graphics.drawString(this.font, this.municipality.getMunicipalityName(), startOffsetLeftMiddle, startOffsetTop, 4210752);
        startOffsetTop += startOffsetTopEach;
        graphics.drawString(this.font, Localization.TEXT_MUNICIPALITY_PLAYER_NUMBER, startOffsetLeft, startOffsetTop, 4210752);
        graphics.drawString(this.font, String.valueOf(this.municipality.getInhabitants().size()), startOffsetLeftMiddle, startOffsetTop, 4210752);
        startOffsetTop += startOffsetTopEach;
        graphics.drawString(this.font, Localization.TEXT_MUNICIPALITY_PLAYER_HAPPINESS, startOffsetLeft, startOffsetTop, 4210752);
        int happinessRGB = calculateHappinessColor(this.municipality.getHappiness());
        graphics.drawString(this.font, String.valueOf(100), startOffsetLeftMiddle, startOffsetTop, happinessRGB);
        super.render(graphics, mouseX, mouseY, partialTick);
        startOffsetTop = this.topPos + 30;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouesY) {
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
        graphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics grapics, int mouseX, int mouseY) {

    }

    private int calculateHappinessColor(int happiness) {
        double happinessFloat = (double) (100 - happiness) / 100;
        int red = (int) (happinessFloat * (255));
        int green = 255 - red;
        Color color = new Color(red, green, 0);
        return color.getRGB();
    }
}
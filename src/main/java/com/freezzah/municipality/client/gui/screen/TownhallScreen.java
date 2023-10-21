package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.municipality.IMunicipality;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;

public class TownhallScreen extends MunicipalityScreen {
    private int totalHeightOffset;
    IMunicipality municipality;
    int textWidth = 20;
    int textHeight = 10;
    int editBoxWidth = 150;
    int editBoxHeight = 20;
    private int offsetText = 60;
    private StringWidget municipalityNameFieldLabel;
    private StringWidget municipalityNameField;
    private StringWidget centerFieldLabel;
    private StringWidget centerField;
    private int marginText = 15;

    public TownhallScreen(IMunicipality municipality, Component component) {
        super(component);
        this.municipality = municipality;
        totalHeightOffset = 0;
    }

    @Override
    protected void init() {
        super.init();

        renderMunicipalityFieldLabel();
        renderMunicipalityField();

        renderCenterFieldLabel();
        renderCenterField();

        renderPlayerFieldLabel();
        renderPlayerField();
    }

    private void renderMunicipalityFieldLabel() {
        municipalityNameFieldLabel = addRenderableWidget(
                new net.minecraft.client.gui.components.StringWidget(calcXMid(textWidth) - offsetText,
                        totalHeightOffset + calcYTop(textHeight) + marginText,
                        textWidth,
                        textHeight,
                        Component.literal(Localization.TEXT_MUNICIPALITY_NAME),
                        this.font));
    }

    private void renderMunicipalityField() {
        municipalityNameField = addRenderableWidget(
                new net.minecraft.client.gui.components.StringWidget(calcXMid(textWidth) + offsetText,
                        totalHeightOffset + calcYTop(textHeight) + marginText,
                        textWidth,
                        textHeight,
                        Component.literal(municipality.getMunicipalityName()),
                        this.font));
        totalHeightOffset += editBoxHeight + 10;
    }

    private void renderCenterFieldLabel() {
        centerFieldLabel = addRenderableWidget(
                new net.minecraft.client.gui.components.StringWidget(calcXMid(textWidth) - offsetText,
                        totalHeightOffset + calcYTop(textHeight) + marginText,
                        textWidth,
                        textHeight,
                        Component.literal(Localization.TEXT_MUNICIPALITY_CENTER),
                        this.font));
    }
    private void renderCenterField() {
        centerField = addRenderableWidget(
                new net.minecraft.client.gui.components.StringWidget(calcXMid(textWidth) + offsetText,
                        totalHeightOffset + calcYTop(textHeight) + marginText,
                        textWidth,
                        textHeight,
                        Component.literal(String.format("%d, %d, %d",
                                municipality.getTownhallBlockPos().getX(),
                                municipality.getTownhallBlockPos().getY(),
                                municipality.getTownhallBlockPos().getZ())),
                        this.font));
        totalHeightOffset += editBoxHeight + 10;
    }

    private void renderPlayerFieldLabel() {
        addRenderableWidget(
                new net.minecraft.client.gui.components.StringWidget(calcXMid(textWidth) - offsetText,
                        totalHeightOffset + calcYTop(textHeight) + marginText,
                        textWidth,
                        textHeight,
                        Component.literal(Localization.TEXT_MUNICIPALITY_PLAYERS),
                        this.font));
        totalHeightOffset += textHeight + 10;
    }

    private void renderPlayerField() {

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private <MSG> void sendToServer(CreateTownhallPacket msg){

    }
}

package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.network.handler.ModPacketHandler;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.freezzah.municipality.Constants.MOD_ID;
import static com.freezzah.municipality.client.Localization.BUTTON_TEXT_CREATE_TOWNHALL;

public class UnclaimedTownhallScreen extends MunicipalityScreen {
    private final BlockPos blockPos;
    private final Inhabitant inhabitant;
    private EditBox nameBox;
    int totalHeightOffset = 0;
    int editBoxWidth = 150;
    int editBoxHeight = 20;
    int textWidth = 10;
    int textHeight = 10;
    int createButtonWidth = 150;
    int createButtonHeight = 20;


    public UnclaimedTownhallScreen(Component title, Inhabitant inhabitant, BlockPos blockPos) {
        super(title);
        this.inhabitant = inhabitant;
        this.blockPos = blockPos;
    }

    @Override
    protected void init() {
        super.init();

        renderMunicipalityNameTextBox();

        renderMunicipalityNameBox();

        renderCreateButton();
    }

    private void renderMunicipalityNameTextBox() {
        addRenderableWidget(
                new net.minecraft.client.gui.components.StringWidget(calcXMid(textWidth),
                        totalHeightOffset + calcYTop(textHeight) + 10,
                        textWidth,
                        textHeight,
                        Component.literal(Localization.TEXT_MUNICIPALITY_NAME),
                        this.font));
        totalHeightOffset += textHeight + 10;
    }

    private void renderMunicipalityNameBox() {
        nameBox = addRenderableWidget(
                new EditBox(
                        this.font,
                        calcXMid(editBoxWidth),
                        calcYTop(editBoxHeight) + 10,
                        editBoxWidth,
                        editBoxHeight,
                        Component.literal("")));
        totalHeightOffset += editBoxHeight + 10;
    }

    private void renderCreateButton() {
        addRenderableWidget(new Button.Builder(
                Component.literal(BUTTON_TEXT_CREATE_TOWNHALL),
                button -> this.sendToServer(new CreateTownhallPacket(inhabitant.getUUID(), blockPos, nameBox.getValue())))
                .size(createButtonWidth, createButtonHeight)
                .pos(calcXMid(createButtonWidth), totalHeightOffset + calcYTop(createButtonHeight) + 10)
                .build());
        totalHeightOffset += createButtonHeight + 10;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private <MSG> void sendToServer(CreateTownhallPacket msg){
        if(msg.getTownhallName() == null || msg.getTownhallName() == ""){
            //TODO name empty
            return;
        }
        else ModPacketHandler.INSTANCE.sendToServer(msg);
        this.onClose();
    }



}

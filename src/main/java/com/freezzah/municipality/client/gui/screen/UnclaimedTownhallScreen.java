package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.menu.UnclaimedTownhallMenu;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.network.handler.ModPacketHandler;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.municipality.client.Localization.BUTTON_TEXT_CREATE_TOWNHALL;

public class UnclaimedTownhallScreen extends MunicipalityScreen<UnclaimedTownhallMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Constants.MOD_ID, "textures/gui/screen/townhall_screen_background.png");
    private final Inhabitant inhabitant;
    @SuppressWarnings("FieldCanBeLocal")
    private final int editBoxWidth = 150;
    @SuppressWarnings("FieldCanBeLocal")
    private final int editBoxHeight = 20;
    @SuppressWarnings("FieldCanBeLocal")
    private final int textWidth = 10;
    @SuppressWarnings("FieldCanBeLocal")
    private final int textHeight = 10;
    @SuppressWarnings("FieldCanBeLocal")
    private final int createButtonWidth = 150;
    @SuppressWarnings("FieldCanBeLocal")
    private final int createButtonHeight = 20;
    int totalHeightOffset = 0;
    private EditBox nameBox;


    public UnclaimedTownhallScreen(UnclaimedTownhallMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.width = 480;
        this.height = 253;
        this.inhabitant = new Inhabitant(playerInventory.player.getUUID(), playerInventory.getName().toString());
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
                button -> this.sendToServer(new CreateTownhallPacket(inhabitant.getUUID(), menu.getBlockPos(), nameBox.getValue())))
                .size(createButtonWidth, createButtonHeight)
                .pos(calcXMid(createButtonWidth), totalHeightOffset + calcYTop(createButtonHeight) + 10)
                .build());
        totalHeightOffset += createButtonHeight + 10;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {

    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    private void sendToServer(CreateTownhallPacket msg) {
        if (msg.townhallName().isEmpty()) {
            //TODO name empty
            return;
        } else ModPacketHandler.INSTANCE.sendToServer(msg);
        this.onClose();
    }
}

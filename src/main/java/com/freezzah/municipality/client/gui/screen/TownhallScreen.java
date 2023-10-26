package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.menu.TownhallMenu;
import com.freezzah.municipality.municipality.Municipality;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TownhallScreen extends MunicipalityScreen<TownhallMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Constants.MOD_ID, "textures/gui/screen/townhall_screen_background.png");
    @SuppressWarnings("FieldCanBeLocal")
    private final int verticalSpacing = 20;
    private final int horizontalSpacing = 20;
    private final Municipality municipality;
    private final Player player;
    private final Inventory playerInventory;
    private int offsetTop = this.topPos + 30; //DYNAMIC
    private int offsetBottom = this.height;
    private int offsetLeft = this.leftPos + 20;
    @SuppressWarnings("FieldCanBeLocal")
    private int startOffsetLeftMiddle = this.imageWidth / 2 + offsetLeft;

    public TownhallScreen(TownhallMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
        this.playerInventory = playerInventory;
        this.municipality = menu.getMunicipality();
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.width = 256;
        this.height = 253;
    }

    @Override
    protected void init() {
        super.init();
        offsetBottom = this.height;
        offsetLeft = this.leftPos + 20;
        addRenderableWidget(new Button.Builder(
                Component.literal(Localization.BUTTON_TEXT_INHABITANTS),
                button -> openInhabitantScreen())
                .pos(offsetLeft, offsetBottom - verticalSpacing - Button.DEFAULT_HEIGHT)
                .size((int) (Button.DEFAULT_WIDTH / 1.5), Button.DEFAULT_HEIGHT)
                .build());
        offsetLeft += (int) (Button.DEFAULT_WIDTH / 1.5 + horizontalSpacing);
        addRenderableWidget(new Button.Builder(
                Component.literal(Localization.BUTTON_DELETE_MUNICIPALITY),
                button -> deleteMunicipality())
                .pos(offsetLeft, offsetBottom - verticalSpacing - Button.DEFAULT_HEIGHT)
                .size((int) (Button.DEFAULT_WIDTH / 1.5), Button.DEFAULT_HEIGHT)
                .build());
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        offsetLeft = this.leftPos + 20;
        startOffsetLeftMiddle = this.imageWidth / 2 + offsetLeft;
        graphics.drawString(this.font, Localization.TEXT_MUNICIPALITY_NAME, offsetLeft, offsetTop, 4210752);
        if (municipality.getMunicipalityName() == null) {
            return;
        }

        graphics.drawString(this.font, this.municipality.getMunicipalityName(), startOffsetLeftMiddle, offsetTop, 4210752);
        offsetTop += verticalSpacing;
        graphics.drawString(this.font, Localization.TEXT_MUNICIPALITY_PLAYER_NUMBER, offsetLeft, offsetTop, 4210752);
        graphics.drawString(this.font, String.valueOf(this.municipality.getInhabitants().size()), startOffsetLeftMiddle, offsetTop, 4210752);
        offsetTop += verticalSpacing;
        graphics.drawString(this.font, Localization.TEXT_MUNICIPALITY_PLAYER_HAPPINESS, offsetLeft, offsetTop, 4210752);
        int happinessRGB = calculateHappinessColor(this.municipality.getHappiness());
        graphics.drawString(this.font, String.valueOf(100), startOffsetLeftMiddle, offsetTop, happinessRGB);
        super.render(graphics, mouseX, mouseY, partialTick);
        offsetTop = this.topPos + 30;

    }

    private void deleteMunicipality() {

    }

    private void openInhabitantScreen() {
        assert this.minecraft != null;
        this.minecraft.setScreen(new InhabitantsScreen(Component.literal("sometitle"), municipality));
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouesY) {
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

    @Override
    public void renderTransparentBackground(@NotNull GuiGraphics graphics) {

    }
}
package com.freezzah.municipality.client.gui.screen;

import com.freezzah.municipality.network.handler.ModPacketHandler;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import static com.freezzah.municipality.Constants.MOD_ID;
import static com.freezzah.municipality.client.Localization.BUTTON_TEXT_CREATE_TOWNHALL;

public class TownhallScreen extends Screen {

    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(MOD_ID, "textures/gui/container/townhall_screen.png");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MOD_ID, "textures/gui/townhall.png");
    private final BlockPos blockPos;

    private final Player player;

    public TownhallScreen(Component title, Player player, BlockPos blockPos) {
        super(title);
        this.player = player;
        this.blockPos = blockPos;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        int width = 150;
        int height = 20;
        addRenderableWidget(new Button.Builder(
                Component.literal(BUTTON_TEXT_CREATE_TOWNHALL),
                button -> ModPacketHandler.INSTANCE.sendToServer(new CreateTownhallPacket(player.getUUID(), blockPos)))
                .size(width, height)
                .pos(calcXMid(width), calcYTop(height) + 10)
                .build());
    }


    private int calcXMid(int width) {
        int mid = this.width / 2;
        int result = mid - width / 2;
        return result;
    }

    private int calcYMid(int height) {
        int mid = this.height / 2;
        int result = mid - height / 2;
        return result;
    }

    private int calcYTop(int height) {
        int top = height;
        return top;
    }
}

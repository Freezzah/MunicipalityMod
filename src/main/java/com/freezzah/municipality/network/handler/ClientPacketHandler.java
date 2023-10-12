package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.screen.TownhallScreen;
import com.freezzah.municipality.network.packet.OpenVacantTownhallViewPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handlePacket(OpenVacantTownhallViewPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = Minecraft.getInstance().level.getPlayerByUUID(msg.getPlayerUid());
            Minecraft.getInstance().setScreen(new TownhallScreen(Component.literal(Localization.SCREEN_TOWNHALL_NAME), player, msg.getTownhallBlockpos()));
            Constants.LOGGER.info("Received OpenTownhallViewPacket on client via ClientPacketHandler");
        });
        ctx.get().setPacketHandled(true);
    }
}

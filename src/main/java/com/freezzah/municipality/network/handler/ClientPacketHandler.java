package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.screen.TownhallScreen;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.network.packet.OpenVacantTownhallViewPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkInstance;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handlePacket(OpenVacantTownhallViewPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Inhabitant inhabitant = Inhabitant.fromPlayer(Minecraft.getInstance().player.level().getPlayerByUUID(msg.getPlayerUuid()));
            Minecraft.getInstance().setScreen(new TownhallScreen(Component.literal(Localization.SCREEN_TOWNHALL_NAME), inhabitant, msg.getTownhallBlockpos()));
            Constants.LOGGER.info("Received OpenTownhallViewPacket on client via ClientPacketHandler");
        });
        ctx.get().setPacketHandled(true);
    }
}

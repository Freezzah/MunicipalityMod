package com.freezzah.municipality.network.packet;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.network.handler.ServerPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record CreateTownhallPacket(@NotNull UUID playerId, @NotNull BlockPos townhallBlockPos,
                                   @NotNull String townhallName) {

    public static void encode(@NotNull CreateTownhallPacket msg, @NotNull FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId());
        buf.writeBlockPos(msg.townhallBlockPos());
        buf.writeComponent(Component.literal(msg.townhallName));
    }

    public static CreateTownhallPacket decode(@NotNull FriendlyByteBuf buf) {
        return new CreateTownhallPacket(
                buf.readUUID(),
                buf.readBlockPos(),
                buf.readComponent().getString());
    }

    public static void handle(@NotNull CreateTownhallPacket msg, NetworkEvent.Context ctx) {
        Constants.LOGGER.info("Received CreateTownhallPacket on server");
        ServerPacketHandler.handlePacket(msg, ctx);
    }
}

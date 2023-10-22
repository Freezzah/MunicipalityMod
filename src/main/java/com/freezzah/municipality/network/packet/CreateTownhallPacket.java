package com.freezzah.municipality.network.packet;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.network.handler.ServerPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record CreateTownhallPacket(UUID playerId, BlockPos townhallBlockPos, String townhallName) {

    public static void encode(CreateTownhallPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId());
        buf.writeBlockPos(msg.townhallBlockPos());
        buf.writeComponent(Component.literal(msg.townhallName));
    }

    public static CreateTownhallPacket decode(FriendlyByteBuf buf) {
        return new CreateTownhallPacket(
                buf.readUUID(),
                buf.readBlockPos(),
                buf.readComponent().getString());
    }

    public static void handle(CreateTownhallPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Constants.LOGGER.info("Received CreateTownhallPacket on server");
        ctx.get().enqueueWork(() ->
                ServerPacketHandler.handlePacket(msg, ctx)
        );
    }
}

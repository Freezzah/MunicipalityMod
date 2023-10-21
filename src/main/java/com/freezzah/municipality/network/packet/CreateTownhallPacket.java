package com.freezzah.municipality.network.packet;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.blocks.entity.TownHallBlockEntity;
import com.freezzah.municipality.network.handler.ServerPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CreateTownhallPacket {
    private final UUID playerId;
    private final BlockPos townhallBlockPos;
    private final String townhallName;

    public CreateTownhallPacket(Player player, TownHallBlockEntity townhall, String townhallName) {
        this.playerId = player.getUUID();
        this.townhallBlockPos = townhall.getBlockPos();
        this.townhallName = townhallName;
    }

    public CreateTownhallPacket(UUID playerId, BlockPos townhallBlockPos, String townhallName) {
        this.playerId = playerId;
        this.townhallBlockPos = townhallBlockPos;
        this.townhallName = townhallName;
    }

    public static void encode(CreateTownhallPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.getPlayerId());
        buf.writeBlockPos(msg.getTownhallBlockPos());
        buf.writeComponent(Component.literal(msg.townhallName));
    }

    public static CreateTownhallPacket decode(FriendlyByteBuf buf) {
        CreateTownhallPacket packet = new CreateTownhallPacket(
                buf.readUUID(),
                buf.readBlockPos(),
                buf.readComponent().getString());
        return packet;
    }

    public static void handle(CreateTownhallPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Constants.LOGGER.info("Received CreateTownhallPacket on server");
        ctx.get().enqueueWork(() ->
                ServerPacketHandler.handlePacket(msg, ctx)
        );
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public BlockPos getTownhallBlockPos() {
        return townhallBlockPos;
    }

    public String getTownhallName() {
        return townhallName;
    }
}

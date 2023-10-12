package com.freezzah.municipality.network.packet;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.network.handler.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;


public class OpenVacantTownhallViewPacket {
    private UUID playerUid;
    private BlockPos townhallBlockpos;

    public OpenVacantTownhallViewPacket() {
    }

    public OpenVacantTownhallViewPacket(UUID playerUid, BlockPos townhallBlockpos) {
        this.playerUid = playerUid;
        this.townhallBlockpos = townhallBlockpos;
    }

    public static void encode(OpenVacantTownhallViewPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerUid);
        buf.writeBlockPos(msg.townhallBlockpos);
    }

    public static OpenVacantTownhallViewPacket decode(FriendlyByteBuf buf) {
        OpenVacantTownhallViewPacket packet = new OpenVacantTownhallViewPacket();
        packet.playerUid = buf.readUUID();
        packet.townhallBlockpos = buf.readBlockPos();
        return packet;
    }

    public static void handle(OpenVacantTownhallViewPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Constants.LOGGER.info("Received OpenTownhallViewPacket on client via OpenTownhallViewPacket::handler");
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlePacket(msg, ctx))
        );
    }

    public UUID getPlayerUid() {
        return playerUid;
    }

    public BlockPos getTownhallBlockpos() {
        return townhallBlockpos;
    }
}

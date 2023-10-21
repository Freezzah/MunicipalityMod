package com.freezzah.municipality.network.packet;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.municipality.IMunicipality;
import com.freezzah.municipality.municipality.Municipality;
import com.freezzah.municipality.network.handler.ClientPacketHandler;
import com.mojang.datafixers.kinds.IdF;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;


public class OpenTownhallViewPacket {
    private UUID playerUid;
    private BlockPos townhallBlockpos;
    private CompoundTag municipalityTag;

    public OpenTownhallViewPacket() {
    }

    public OpenTownhallViewPacket(UUID playerUid, BlockPos townhallBlockpos, CompoundTag municipalityTag) {
        this.playerUid = playerUid;
        this.townhallBlockpos = townhallBlockpos;
        this.municipalityTag = municipalityTag;
    }

    public static void encode(OpenTownhallViewPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerUid);
        buf.writeBlockPos(msg.townhallBlockpos);
        buf.writeNbt(msg.municipalityTag);
    }

    public static OpenTownhallViewPacket decode(FriendlyByteBuf buf) {
        OpenTownhallViewPacket packet = new OpenTownhallViewPacket();
        packet.playerUid = buf.readUUID();
        packet.townhallBlockpos = buf.readBlockPos();
        packet.municipalityTag = buf.readNbt();
        return packet;
    }

    public static void handle(OpenTownhallViewPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Constants.LOGGER.info("Received OpenTownhallViewPacket on client via OpenTownhallViewPacket::handler");
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlePacket(msg, ctx))
        );
    }

    public UUID getPlayerUuid() {
        return playerUid;
    }

    public BlockPos getTownhallBlockpos() {
        return townhallBlockpos;
    }

    public IMunicipality getMunicipality() {
        return Municipality.load(this.municipalityTag, Minecraft.getInstance().level);
    }
}

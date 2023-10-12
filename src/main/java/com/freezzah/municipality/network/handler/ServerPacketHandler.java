package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.blocks.TownhallBlock;
import com.freezzah.municipality.blocks.entity.TownHallBlockEntity;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerPacketHandler {
    public static void handlePacket(CreateTownhallPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            BlockPos pos = msg.getTownhallBlockPos();
            Level level = ctx.get().getSender().level();
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof TownhallBlock) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof TownHallBlockEntity) {
//                    ((TownHallBlockEntity) blockEntity).createTown(ctx.get().getSender()); //TODO
                    Constants.LOGGER.info("Succesfully delegated createTownhallEvent to server");
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

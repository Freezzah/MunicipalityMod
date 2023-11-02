package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.blocks.TownhallBlock;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.municipality.Municipality;
import com.freezzah.municipality.municipality.MunicipalityManager;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

public class ServerPacketHandler {
    public static void handlePacket(@NotNull CreateTownhallPacket msg, @NotNull NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            BlockPos pos = msg.townhallBlockPos();
            ServerPlayer sender = ctx.getSender();
            if (sender == null) {
                return;
            }
            Level level = sender.level();
            BlockState state = level.getBlockState(pos);

            //Validate position
            //noinspection deprecation
            if (level.hasChunkAt(pos)) {
                //Validate townhall
                if (state.getBlock() instanceof TownhallBlock) {
                    //On server side
                    if (!level.isClientSide) {
                        MunicipalityManager manager = new MunicipalityManager((ServerLevel) level);

                        Player player = level.getPlayerByUUID(msg.playerId());
                        if (player == null) {
                            return;
                        }
                        Municipality municipality = manager.createMunicipalityWithPlayer(msg.townhallBlockPos(), player, msg.townhallName());
                        //Create failed if null
                        if (municipality == null) {
                            player.sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_MUNICIPALITY_FAILED));
                        } else {

                            player.sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_MUNICIPALITY(msg.townhallName(), player.getName())));
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}

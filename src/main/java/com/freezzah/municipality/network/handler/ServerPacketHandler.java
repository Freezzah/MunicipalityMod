package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.blocks.TownhallBlock;
import com.freezzah.municipality.blocks.entity.TownHallBlockEntity;
import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.municipality.IMunicipality;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.freezzah.municipality.MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY;

public class ServerPacketHandler {
    public static void handlePacket(CreateTownhallPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            BlockPos pos = msg.townhallBlockPos();
            Level level = ctx.get().getSender().level();
            BlockState state = level.getBlockState(pos);

            //Validate position
            //noinspection deprecation
            if(level.hasChunkAt(pos)){
                //Validate townhall
                if (state.getBlock() instanceof TownhallBlock) {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    //Validate BE
                    if (blockEntity instanceof TownHallBlockEntity) {
                        //On server side
                        if(!level.isClientSide) {
                            IMunicipalityManagerCapability cap = level
                                    .getCapability(MUNICIPALITY_MANAGER_CAPABILITY, null).resolve().orElse(null);
                            Player player = level.getPlayerByUUID(msg.playerId());
                            IMunicipality municipality = cap.createMunicipalityWithPlayer(level, msg.townhallBlockPos(), player, msg.townhallName());
                            //Create failed if null
                            if(municipality == null){
                                level.getPlayerByUUID(msg.playerId()).sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_MUNICIPALITY_FAILED));
                            } else {
                                level.getPlayerByUUID(msg.playerId()).sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_MUNICIPALITY(msg.townhallName(), player.getName())));
                            }
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

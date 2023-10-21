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
            BlockPos pos = msg.getTownhallBlockPos();
            Level level = ctx.get().getSender().level();
            BlockState state = level.getBlockState(pos);

            //Validate position
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
                            Player player = level.getPlayerByUUID(msg.getPlayerId());
                            IMunicipality municipality = cap.createMunicipalityWithPlayer(level, msg.getTownhallBlockPos(), player, msg.getTownhallName());
                            //Create failed if null
                            if(municipality == null){
                                level.getPlayerByUUID(msg.getPlayerId()).sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_MUNICIPALITY_FAILED(msg.getTownhallName(), player.getName())));
                            } else {
                                level.getPlayerByUUID(msg.getPlayerId()).sendSystemMessage(Component.literal(Localization.MUNICIPALITY_NEW_MUNICIPALITY(msg.getTownhallName(), player.getName())));
                            }
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

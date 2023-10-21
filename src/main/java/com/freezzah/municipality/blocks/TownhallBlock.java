package com.freezzah.municipality.blocks;

import com.freezzah.municipality.MunicipalityMod;
import com.freezzah.municipality.blocks.entity.TownHallBlockEntity;
import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.client.gui.menu.TownhallMenu;
import com.freezzah.municipality.municipality.IMunicipality;
import com.freezzah.municipality.network.handler.ModPacketHandler;
import com.freezzah.municipality.network.packet.OpenTownhallViewPacket;
import com.freezzah.municipality.network.packet.OpenVacantTownhallViewPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TownhallBlock extends MunicipalityBlock {

    public TownhallBlock(String municipalityBlockname) {
        super(municipalityBlockname);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {

        return new SimpleMenuProvider((containerId, playerInventory, player) -> new TownhallMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)), Component.translatable("menu.title.municipality.mymenu"));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        IMunicipalityManagerCapability cap = level.getCapability(MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY).orElseThrow(null);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            IMunicipality municipality = cap.getMunicipalityByBlockPos(pos);
            if(municipality != null){
                ModPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new OpenTownhallViewPacket(
                    player.getUUID(), pos, municipality.getMunicipalityTag()
                ));
            }
            else {
                ModPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new OpenVacantTownhallViewPacket(player.getUUID(), pos));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TownHallBlockEntity(pos, state);
    }
}

package com.freezzah.municipality.blocks;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.MunicipalityMod;
import com.freezzah.municipality.blocks.building.EnumBuilding;
import com.freezzah.municipality.blocks.building.IBuilding;
import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.caps.MunicipalityManagerCapability;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.menu.BankMenu;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.Municipality;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BankBlock extends MunicipalityBlock {

    public BankBlock(@NotNull String municipalityBlockname) {
        super(municipalityBlockname);
    }


    public boolean onPlaceBy(@NotNull Player player, @NotNull Block ignoredBlock, @NotNull Level level, @NotNull BlockPos pos) {

        IMunicipalityManagerCapability cap = level.getCapability(MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY).orElse(
                new MunicipalityManagerCapability()); //TODO can this be done?
        Municipality municipality = cap.getMunicipalityByInhabitant(Inhabitant.fromPlayer(player));
        IBuilding building;
        if (municipality == null)
            return true;
        building = EnumBuilding.fromByteType(EnumBuilding.EnumIds.BANK_BYTE, municipality, pos);
        if (building == null)
            return true;
        //AddBuilding returns success, so we invert the result to return shouldCancel.
        return !municipality.addBuilding(building, player);
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        @SuppressWarnings("DataFlowIssue") // TODO
        IMunicipalityManagerCapability cap = level.getCapability(MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY).orElse(null);
        Municipality municipality = cap.getMunicipalityByBlockPos(pos);
        if (municipality == null) {
            Constants.LOGGER.error("Bank municipality is null");
            return null;
        } else {
            FriendlyByteBuf buf = municipality.putInFriendlyByteBuf(new FriendlyByteBuf(Unpooled.buffer()));
            CompoundTag c = buf.readNbt();
            buf.writeNbt(c);
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new BankMenu(containerId, playerInventory, buf),
                    Component.translatable(Localization.BANK_MENU_TITLE));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        @SuppressWarnings("DataFlowIssue") // TODO
        IMunicipalityManagerCapability cap = level.getCapability(MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY).orElseThrow(null);
        Municipality municipality = cap.getMunicipalityByBlockPos(pos);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (municipality == null) {
                serverPlayer.openMenu(state.getMenuProvider(level, pos), buf -> buf.writeBlockPos(pos));
            } else {
                serverPlayer.openMenu(state.getMenuProvider(level, pos), municipality::putInFriendlyByteBuf);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}

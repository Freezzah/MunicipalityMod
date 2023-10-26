package com.freezzah.municipality.blocks;

import com.freezzah.municipality.MunicipalityMod;
import com.freezzah.municipality.blocks.entity.TownHallBlockEntity;
import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.menu.TownhallMenu;
import com.freezzah.municipality.client.gui.menu.UnclaimedTownhallMenu;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TownhallBlock extends MunicipalityBlock {

    public TownhallBlock(String municipalityBlockname) {
        super(municipalityBlockname);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, Level level, @NotNull BlockPos pos) {
        @SuppressWarnings("DataFlowIssue") // TODO
        IMunicipalityManagerCapability cap = level.getCapability(MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY).orElse(null);
        Municipality municipality = cap.getMunicipalityByBlockPos(pos);
        if (municipality == null) {
            // Unclaimed townhall
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos);
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new UnclaimedTownhallMenu(containerId, playerInventory, buf),
                    Component.translatable(Localization.UNCLAIMED_TOWNHALL_MENU_TITLE));
        } else {
            // Claimed townhall
            FriendlyByteBuf buf = municipality.putInFriendlyByteBuf(new FriendlyByteBuf(Unpooled.buffer()));
            CompoundTag c = buf.readNbt();
            buf.writeNbt(c);
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new TownhallMenu(containerId, playerInventory, buf),
                    Component.translatable(Localization.TOWNHALL_MENU_TITLE));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TownHallBlockEntity(pos, state);
    }
}

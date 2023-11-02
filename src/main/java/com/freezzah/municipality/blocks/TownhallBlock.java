package com.freezzah.municipality.blocks;

import com.freezzah.municipality.client.Localization;
import com.freezzah.municipality.client.gui.menu.TownhallMenu;
import com.freezzah.municipality.client.gui.menu.UnclaimedTownhallMenu;
import com.freezzah.municipality.municipality.Municipality;
import com.freezzah.municipality.municipality.MunicipalityManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TownhallBlock extends MunicipalityBlock {

    public TownhallBlock(@NotNull String municipalityBlockname) {
        super(municipalityBlockname);
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        MunicipalityManager manager = new MunicipalityManager((ServerLevel) level);
        Municipality municipality = manager.getMunicipalityByBlockPos(pos);
        if (municipality == null) {
            // Unclaimed townhall
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new UnclaimedTownhallMenu(containerId, playerInventory),
                    Component.translatable(Localization.UNCLAIMED_TOWNHALL_MENU_TITLE));
        } else {
            // Claimed townhall
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new TownhallMenu(containerId, playerInventory),
                    Component.translatable(Localization.TOWNHALL_MENU_TITLE));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            MunicipalityManager manager = new MunicipalityManager((ServerLevel) level);
            Municipality municipality = manager.getMunicipalityByBlockPos(pos);

            if (player instanceof ServerPlayer serverPlayer) {
                if (municipality == null) {
                    NetworkHooks.openScreen(serverPlayer, state.getMenuProvider(level, pos), pos);
                } else {
                    NetworkHooks.openScreen(serverPlayer, state.getMenuProvider(level, pos), municipality::putInFriendlyByteBuf);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}

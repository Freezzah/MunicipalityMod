package com.freezzah.municipality.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public interface IMunicipalityBlock {

    /***
     *
     * @param pos Position of the block
     * @param level Level reference
     * @param state State reference
     * @param player Player
     * @return true if break is allowed, otherwise false.
     */
    boolean onBreak(@NotNull BlockPos pos, @NotNull LevelAccessor level, @NotNull BlockState state, @NotNull Player player);
}

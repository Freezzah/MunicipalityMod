package com.freezzah.municipality.blocks;

import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.Municipality;
import com.freezzah.municipality.municipality.MunicipalityManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class MunicipalityBlock extends Block implements IMunicipalityBlock {

    protected MunicipalityBlock(@NotNull String ignoredMunicipalityBlockName) {
        super(Properties.of());
    }

    @Override
    public boolean onBreak(@NotNull BlockPos pos, @NotNull LevelAccessor level, @NotNull BlockState state, @NotNull Player player) {
        if (level instanceof ServerLevel serverLevel) {
            MunicipalityManager manager = new MunicipalityManager(serverLevel);
            Municipality municipality = manager.getMunicipalityByBlockPos(pos);
            if (municipality != null && municipality.isInhabitant(Inhabitant.fromPlayer(player))) {
                return municipality.removeBuilding(pos);

            }
        }
        return false;
    }
}

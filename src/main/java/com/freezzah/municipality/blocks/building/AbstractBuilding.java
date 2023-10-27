package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBuilding implements IBuilding {
    private final BlockPos blockPos;
    private final Municipality municipality;

    public AbstractBuilding(@NotNull Municipality municipality, @NotNull BlockPos blockPos) {
        this.municipality = municipality;
        this.blockPos = blockPos;
    }

    @Override
    public @NotNull Municipality getMunicipality() {
        return municipality;
    }

    @Override
    public @NotNull BlockPos getBlockPos() {
        return blockPos;
    }

    @Override
    public @NotNull CompoundTag serializeNBT(@NotNull CompoundTag compoundTag) {
        return compoundTag;
    }

    @Override
    public @NotNull CompoundTag deserializeNBT(@NotNull CompoundTag compoundTag) {
        return compoundTag;
    }
}

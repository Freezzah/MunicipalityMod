package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public interface IBuilding {
    @NotNull Municipality getMunicipality();

    @NotNull BlockPos getBlockPos();

    @NotNull CompoundTag serializeNBT(@NotNull CompoundTag compoundTag);

    @NotNull CompoundTag deserializeNBT(@NotNull CompoundTag compoundTag);

    byte getType();
}

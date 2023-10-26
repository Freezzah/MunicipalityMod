package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public interface IBuilding {
    Municipality getMunicipality();

    BlockPos getBlockPos();

    CompoundTag serializeNBT(CompoundTag compoundTag);

    CompoundTag deserializeNBT(CompoundTag compoundTag);

    byte getType();
}

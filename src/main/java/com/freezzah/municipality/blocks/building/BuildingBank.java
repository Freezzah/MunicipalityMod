package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class BuildingBank extends AbstractBuilding {

    @SuppressWarnings("unused")
    public static final byte type = EnumBuilding.EnumIds.BANK_BYTE;

    public BuildingBank(@NotNull Municipality municipality, @NotNull BlockPos blockPos) {
        super(municipality, blockPos);
    }

    @Override
    public byte getType() {
        return EnumBuilding.EnumIds.BANK_BYTE;
    }
}
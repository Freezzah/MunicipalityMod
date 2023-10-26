package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;

public class BuildingTownhall extends AbstractBuilding {

    public static final byte type = EnumBuilding.EnumIds.TOWNHALL_BYTE;

    public BuildingTownhall(Municipality municipality, BlockPos blockPos) {
        super(municipality, blockPos);
    }

    @Override
    public byte getType() {
        return EnumBuilding.EnumIds.TOWNHALL_BYTE;
    }
}
package com.freezzah.municipality.blocks;

import net.minecraft.world.level.block.BaseEntityBlock;

public abstract class MunicipalityBlock extends BaseEntityBlock implements IMunicipalityBlock {

    private final String municipalityBlockName;

    protected MunicipalityBlock(String municipalityBlockName) {
        super(Properties.of());
        this.municipalityBlockName = municipalityBlockName;
    }

    public String getBuildingName() {
        return municipalityBlockName;
    }
}

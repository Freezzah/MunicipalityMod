package com.freezzah.municipality.blocks;

import net.minecraft.world.level.block.BaseEntityBlock;
import org.jetbrains.annotations.NotNull;

public abstract class MunicipalityBlock extends BaseEntityBlock implements IMunicipalityBlock {
    private final String municipalityBlockName;

    protected MunicipalityBlock(@NotNull String municipalityBlockName) {
        super(Properties.of());
        this.municipalityBlockName = municipalityBlockName;
    }

    public @NotNull String getBuildingName() {
        return municipalityBlockName;
    }
}

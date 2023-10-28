package com.freezzah.municipality.blocks;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class MunicipalityBlock extends Block implements IMunicipalityBlock {

    protected MunicipalityBlock(@NotNull String ignoredMunicipalityBlockName) {
        super(Properties.of());
    }
}

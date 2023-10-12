package com.freezzah.municipality.blocks;

import com.freezzah.municipality.blocks.entity.BankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BankBlock extends MunicipalityBlock {

    public BankBlock(String municipalityBlockname) {
        super(municipalityBlockname);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BankBlockEntity(blockPos, blockState);
    }
}

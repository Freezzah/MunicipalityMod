package com.freezzah.municipality.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BankBlockEntity extends BlockEntity {
    public BankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntity.BANK_BLOCK_ENTITY.get(), blockPos, blockState);
    }
}

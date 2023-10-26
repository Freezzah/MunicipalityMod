package com.freezzah.municipality.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BankBlockEntity extends MunicipalityBlockEntity {
    private final int xSlots = 9;
    private final int ySlots = 3;
    private final NonNullList<ItemStack> stacks = NonNullList.createWithCapacity(xSlots * ySlots);


    public BankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntity.BANK_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        ContainerHelper.loadAllItems(compoundTag, this.stacks);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.stacks);
    }

}

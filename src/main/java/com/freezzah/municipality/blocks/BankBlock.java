package com.freezzah.municipality.blocks;

import com.freezzah.municipality.blocks.entity.BankBlockEntity;
import com.freezzah.municipality.client.gui.menu.BankMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BankBlock extends MunicipalityBlock {

    public BankBlock(String municipalityBlockname) {
        super(municipalityBlockname);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BankBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos) {
        return new SimpleMenuProvider(
                (containerId, playerInventory, player) -> new BankMenu(containerId, playerInventory, new FriendlyByteBuf(Unpooled.buffer())),
                Component.translatable("menu.title.municipality.bankmenu"));
    }
}

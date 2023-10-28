package com.freezzah.municipality.client.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnclaimedTownhallMenu extends AbstractContainerMenu {

    private final BlockPos blockPos;

    public UnclaimedTownhallMenu(int containerId, Inventory ignoredInv, @NotNull FriendlyByteBuf buf) {
        super(ModMenuType.UNCLAIMED_TOWNHALL_MENU.get(), containerId);
        this.blockPos = buf.readBlockPos();
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        return sourceStack.copy();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true; //todo
    }

    @Nullable
    public BlockPos getBlockPos() {
        return blockPos;
    }
}

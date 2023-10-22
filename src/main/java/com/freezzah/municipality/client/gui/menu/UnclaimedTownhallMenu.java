package com.freezzah.municipality.client.gui.menu;

import com.freezzah.municipality.blocks.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class UnclaimedTownhallMenu extends AbstractContainerMenu {

    private final BlockPos blockPos;
    private final ContainerLevelAccess access;

    public UnclaimedTownhallMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        super(ModMenuType.UNCLAIMED_TOWNHALL_MENU.get(), containerId);
        access = ContainerLevelAccess.NULL;
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
        return AbstractContainerMenu.stillValid(this.access, player, ModBlock.TOWNHALL_BLOCK.get());
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}

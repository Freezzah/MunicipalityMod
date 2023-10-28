package com.freezzah.municipality.client.gui.menu;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TownhallMenu extends AbstractContainerMenu {
    private Municipality municipality;

    public TownhallMenu(int containerId, Inventory ignoredInv, @NotNull FriendlyByteBuf buf) {
        super(ModMenuType.TOWNHALL_MENU.get(), containerId);
        if (!(buf.readerIndex() == 0 && buf.writerIndex() == 0)) {
            this.municipality = Municipality.fromFriendlyByteBuf(buf);
        }
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
    public Municipality getMunicipality() {
        return municipality;
    }
}

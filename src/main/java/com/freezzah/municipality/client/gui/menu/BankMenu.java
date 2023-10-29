package com.freezzah.municipality.client.gui.menu;

import com.freezzah.municipality.items.MunicipalityKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BankMenu extends AbstractContainerMenu {
    public BankMenu(int containerId, Inventory ignoredPlayerInventory, @SuppressWarnings("unused") FriendlyByteBuf buf) {
        super(ModMenuType.BANK_MENU.get(), containerId);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int p_38942_) {
        return new ItemStack(new MunicipalityKey());
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}

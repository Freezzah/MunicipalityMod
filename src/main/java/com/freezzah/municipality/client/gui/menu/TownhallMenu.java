package com.freezzah.municipality.client.gui.menu;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.blocks.ModBlock;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TownhallMenu extends AbstractContainerMenu {

    private ContainerLevelAccess access;

    public TownhallMenu(int containerId, Inventory inv, ContainerLevelAccess access) {
        super(ModMenuType.TOWNHALL_MENU.get(), containerId);
        this.access = access;
    }

    public TownhallMenu(int containerId, Inventory inv) {
        this(containerId, inv, ContainerLevelAccess.NULL);
        access = ContainerLevelAccess.NULL;
    }

    public void createTownhall() {
        Constants.LOGGER.info("CREATE TOWNHALL CALLED");
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, ModBlock.TOWNHALL_BLOCK.get());
    }
}

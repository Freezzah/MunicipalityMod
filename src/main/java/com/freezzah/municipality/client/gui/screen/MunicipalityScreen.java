package com.freezzah.municipality.client.gui.screen;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class MunicipalityScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    protected MunicipalityScreen(T menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    protected int calcXMid(int width) {
        int mid = this.width / 2;
        return mid - width / 2;
    }

    protected int calcYTop(int height) {
        return height;
    }
}

package com.freezzah.municipality.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class MunicipalityScreen extends Screen {

    protected MunicipalityScreen(Component component) {
        super(component);
    }

    protected int calcXMid(int width) {
        int mid = this.width / 2;
        int result = mid - width / 2;
        return result;
    }

    protected int calcYMid(int height) {
        int mid = this.height / 2;
        int result = mid - height / 2;
        return result;
    }

    protected int calcYTop(int height) {
        int top = height;
        return top;
    }
}

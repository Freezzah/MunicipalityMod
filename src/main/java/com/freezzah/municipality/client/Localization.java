package com.freezzah.municipality.client;

import net.minecraft.network.chat.Component;

public class Localization {

    public static final String SCREEN_TOWNHALL_NAME = "Townhall";
    public static final String BUTTON_TEXT_CREATE_TOWNHALL = "Create townhall";
    public static final String SCREEN_BANK_NAME = "Bank";
    public static final String TEXT_MUNICIPALITY_NAME = "Municipality Name";
    public static final String TEXT_MUNICIPALITY_PLAYER_NUMBER = "Amount of players";
    public static final String TEXT_MUNICIPALITY_PLAYER_HAPPINESS = "Happiness";
    public static final String MUNICIPALITY_NEW_MUNICIPALITY_FAILED = "Failed to create municipality";

    public static String MUNICIPALITY_NEW_OWNER(String municipalityName, Component oldOwnerName, Component newOwnerName) {
        return String.format("%s has transferred the ownership of %s to %s", oldOwnerName, municipalityName, newOwnerName);
    }

    public static String MUNICIPALITY_NEW_MUNICIPALITY(String municipalityName, Component newOwnerName) {
        return String.format("%s has created a new Municipality %s!", newOwnerName, municipalityName);
    }


}

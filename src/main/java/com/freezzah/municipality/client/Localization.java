package com.freezzah.municipality.client;

import net.minecraft.network.chat.Component;

public class Localization {

    public static final String SCREEN_TOWNHALL_NAME = "Townhall";
    public static final String BUTTON_TEXT_CREATE_TOWNHALL = "Create townhall";
    public static final String ERROR_TOWNHALL_NOT_OWNER = "You are not the owner of this townhall";
    public static final String ERROR_TOWNHALL_ALREADY_ACTIVE = "Townhall is already active";
    public static final String SUCCESS_TOWNHALL_CREATED = "You succesfully created a townhall!";
    public static final String ERROR_TOWNHALL_BREAK_ACTIVE_MUNICIPALITY = "You cannot break a townhall with an active municipality";
    public static final String SCREEN_BANK_NAME = "Bank";
    public static final String TOWNHALL_NAME_NOT_EMPTY = "Townhall name cannot be empty";
    public static final String TEXT_MUNICIPALITY_NAME = "Municipality Name";
    public static final String TEXT_MUNICIPALITY_PLAYERS = "Players";
    public static final String TEXT_MUNICIPALITY_CENTER = "Townhall location";

    public static String MUNICIPALITY_NEW_OWNER(String municipalityName, Component oldOwnerName, Component newOwnerName) {
        return String.format("%s has transferred the ownership of %s to %s", oldOwnerName, municipalityName, newOwnerName);
    }

    public static String MUNICIPALITY_NEW_MUNICIPALITY(String municipalityName, Component newOwnerName) {
        return String.format("%s has created a new Municipality %s!", municipalityName, newOwnerName);
    }

    public static String MUNICIPALITY_NEW_MUNICIPALITY_FAILED(String test, Component name) {
        return "Failed to create municipality";
    }
}

package com.freezzah.municipality.municipality;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IMunicipality {
    String getMunicipalityName();

    void setMunicipalityName(String municipalityName);

    List<Player> getPlayers();

    void addPlayer(Player player);

    CompoundTag getMunicipalityTag();

    boolean isOwner(Player player);

    void setOwner(Player player);
}

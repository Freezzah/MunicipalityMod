package com.freezzah.municipality.municipality;

import com.freezzah.municipality.entity.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public interface IMunicipality {
    String getMunicipalityName();

    void setMunicipalityName(String municipalityName);

    List<Inhabitant> getInhabitants();

    void addInhabitant(Inhabitant inhabitant);

    UUID getId();

    ResourceKey<Level> getDimensionId();

    Inhabitant getOwner();

    CompoundTag getMunicipalityTag();

    boolean isOwner(Inhabitant inhabitant);

    void setOwner(Inhabitant inhabitant);

    BlockPos getTownhallBlockPos();

    List<Player> getInhabitantsAsPlayers(Level level);
}

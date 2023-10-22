package com.freezzah.municipality.municipality;

import com.freezzah.municipality.entity.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IMunicipality {
    String getMunicipalityName();

    void setMunicipalityName(String municipalityName);

    List<Inhabitant> getInhabitants();

    int getHappiness();

    FriendlyByteBuf putInByteBuf(FriendlyByteBuf friendlyByteBuf);

    CompoundTag getMunicipalityTag();

    boolean isOwner(Inhabitant inhabitant);

    void setOwner(Inhabitant inhabitant);

    BlockPos getTownhallBlockPos();

    List<Player> getInhabitantsAsPlayers(Level level);
}

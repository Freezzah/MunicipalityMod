package com.freezzah.municipality.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public interface IInhabitant {

    Player toPlayer(Level level);

    UUID getUUID();

    String getName();

}

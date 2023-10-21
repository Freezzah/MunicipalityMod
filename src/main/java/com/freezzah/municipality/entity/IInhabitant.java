package com.freezzah.municipality.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public interface IInhabitant {

    Player toPlayer(Level level);

    public UUID getUUID();
    public void setUUID(UUID uuid);
    public String getName();
    public void setName(String name);
    public static Inhabitant fromPlayer(Player player){
        return new Inhabitant(player.getUUID(), player.getName().toString());
    }
}

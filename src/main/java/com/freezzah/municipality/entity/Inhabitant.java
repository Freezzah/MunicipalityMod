package com.freezzah.municipality.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class Inhabitant implements IInhabitant {
    private UUID uuid;
    private String name;

    public Inhabitant(){}

    public Inhabitant(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    public static Inhabitant fromPlayer(Player player) {
        return new Inhabitant(player.getUUID(), player.getName().toString());
    }

    @Override
    public Player toPlayer(Level level){
        return level.getPlayerByUUID(this.uuid);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

package com.freezzah.municipality.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Inhabitant implements IInhabitant {
    private final UUID uuid;
    private final String name;

    public Inhabitant(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Contract("_ -> new")
    public static @NotNull Inhabitant fromPlayer(@NotNull Player player) {
        return new Inhabitant(player.getUUID(), player.getName().toString());
    }

    @Override
    public @Nullable Player toPlayer(@NotNull Level level) {
        return level.getPlayerByUUID(this.uuid);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

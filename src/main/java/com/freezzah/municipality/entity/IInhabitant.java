package com.freezzah.municipality.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IInhabitant {

    @Nullable Player toPlayer(@NotNull Level level);

    UUID getUUID();

    String getName();

}

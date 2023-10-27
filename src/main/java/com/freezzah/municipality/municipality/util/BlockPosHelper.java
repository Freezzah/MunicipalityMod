package com.freezzah.municipality.municipality.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockPosHelper {

    public static @NotNull CompoundTag write(@NotNull CompoundTag tag, @NotNull BlockPos pos, @NotNull String name) {
        CompoundTag coordsCompound = new CompoundTag();
        coordsCompound.putInt("x", pos.getX());
        coordsCompound.putInt("y", pos.getY());
        coordsCompound.putInt("z", pos.getZ());
        tag.put(name, coordsCompound);
        return tag;
    }

    public static @Nullable BlockPos read(CompoundTag tag, String name) {
        final CompoundTag coordsCompound = tag.getCompound(name);
        if (coordsCompound.isEmpty()) {
            return null;
        }
        final int x = coordsCompound.getInt("x");
        final int y = coordsCompound.getInt("y");
        final int z = coordsCompound.getInt("z");
        return new BlockPos(x, y, z);
    }
}

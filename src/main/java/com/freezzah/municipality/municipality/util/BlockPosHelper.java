package com.freezzah.municipality.municipality.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class BlockPosHelper {

    public static CompoundTag write(CompoundTag tag, BlockPos pos, String name) {
        CompoundTag coordsCompound = new CompoundTag();
        coordsCompound.putInt("x", pos.getX());
        coordsCompound.putInt("y", pos.getY());
        coordsCompound.putInt("z", pos.getZ());
        tag.put(name, coordsCompound);
        return tag;
    }

    public static BlockPos read(CompoundTag tag, String name) {
        final CompoundTag coordsCompound = tag.getCompound(name);
        final int x = coordsCompound.getInt("x");
        final int y = coordsCompound.getInt("y");
        final int z = coordsCompound.getInt("z");
        return new BlockPos(x, y, z);
    }
}

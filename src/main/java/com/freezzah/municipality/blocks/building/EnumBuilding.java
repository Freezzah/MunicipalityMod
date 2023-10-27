package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public enum EnumBuilding {
    TOWNHALL(BuildingTownhall::new, EnumIds.TOWNHALL_BYTE);

    private final BiFunction<Municipality, BlockPos, IBuilding> function;
    private final byte id;

    EnumBuilding(@NotNull BiFunction<Municipality, BlockPos, IBuilding> function, byte id) {
        this.function = function;
        this.id = id;
    }

    public static @Nullable IBuilding fromByteType(byte type, @NotNull Municipality m, @NotNull BlockPos pos) {
        for (EnumBuilding b : EnumBuilding.values()) {
            if (b.id == type) {
                return b.function.apply(m, pos);
            }
        }
        return null;
    }

    public static class EnumIds {
        public static byte TOWNHALL_BYTE = (byte) 1;
    }
}

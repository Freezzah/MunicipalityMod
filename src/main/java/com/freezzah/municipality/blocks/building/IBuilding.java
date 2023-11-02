package com.freezzah.municipality.blocks.building;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public interface IBuilding {
    /**
     * Returns the {@link Municipality} the building belongs to
     *
     * @return {@link Municipality}
     */
    @NotNull Municipality getMunicipality();

    /**
     * Returns the {@link BlockPos} of the building.
     *
     * @return {@link BlockPos}
     */
    @NotNull BlockPos getBlockPos();

    /**
     * Serializes the {@link IBuilding} to an existing {@link CompoundTag}
     * Note that it will only serialize additional data. The {@link IBuilding#getType()} and
     * {@link IBuilding#getMunicipality()} will not be serialized by this function.
     *
     * @param compoundTag {@link CompoundTag} to put the serialized {@link IBuilding} into
     * @return {@link CompoundTag} with serialized {@link IBuilding}
     */
    @NotNull CompoundTag serializeNBT(@NotNull CompoundTag compoundTag);

    /**
     * Deerializes the {@link CompoundTag} and populates the current object.
     * Note that it will only deserialize additional data. The {@link IBuilding#getType()} and
     * {@link IBuilding#getMunicipality()} must already be set by this finction.
     *
     * @param compoundTag {@link CompoundTag} to deserialize the {@link IBuilding} from
     * @return {@link CompoundTag} leftovers of the {@link CompoundTag}
     */
    @NotNull CompoundTag deserializeNBT(@NotNull CompoundTag compoundTag);


    /**
     * Returns the byte type of the instance, as per {@link EnumBuilding#values()}
     * This byte can be used, together with {@link IBuilding#getMunicipality()} to construct the
     * {@link IBuilding} using {@link EnumBuilding#fromByteType(byte, Municipality, BlockPos)}
     *
     * @return Byte type representation of the instance
     */
    byte getType();
}

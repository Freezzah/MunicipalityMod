package com.freezzah.municipality.caps;

import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IMunicipalityManagerCapability {

    String TAG_MUNICIPALITIES = "municipalities";

    static @NotNull Tag writeNbt(Capability<IMunicipalityManagerCapability> municipalityManagerCapability, IMunicipalityManagerCapability municipalityCapManager) {
        final CompoundTag compoundTag = new CompoundTag();
        List<Municipality> municipalities = municipalityCapManager.getMunicipalities();
        Stream<CompoundTag> tags = municipalities.stream().map(Municipality::getMunicipalityTag);
        compoundTag.put(TAG_MUNICIPALITIES, tags.filter(Objects::nonNull).collect(toListNBT()));
        return compoundTag;
    }

    static void readNbt(Capability<IMunicipalityManagerCapability> municipalityManagerCapability,
                        IMunicipalityManagerCapability instance,
                        Direction side, Tag nbt) {
        final CompoundTag compound = (CompoundTag) nbt;
        for (final Tag tag : compound.getList(TAG_MUNICIPALITIES, Tag.TAG_COMPOUND)) {
            final Municipality municipality = Municipality.load((CompoundTag) tag);
            if (municipality != null) {
                instance.addMunicipality(municipality);
            }
        }

    }

    @Contract(" -> new")
    static @NotNull Collector<CompoundTag, ?, ListTag> toListNBT() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    final ListTag tagList = new ListTag();
                    tagList.addAll(list);

                    return tagList;
                });
    }

    @Nullable Municipality createMunicipalityWithPlayer(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull String townhallName);

    @NotNull List<Municipality> getMunicipalities();

    @Nullable Municipality getMunicipalityByInhabitant(@NotNull Inhabitant inhabitant);

    boolean existsPlayerInAnyMunicipality(@NotNull Player player);

    boolean existMunicipalityAtBlock(@NotNull BlockPos pos);

    void addMunicipality(@NotNull Municipality municipality);

    @Nullable Municipality getMunicipalityByBlockPos(@NotNull BlockPos pos);
}

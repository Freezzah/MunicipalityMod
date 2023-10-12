package com.freezzah.municipality.caps;

import com.freezzah.municipality.municipality.IMunicipality;
import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface IMunicipalityManagerCapability {

    String TAG_MUNICIPALITIES = "municipalities";

    static Tag writeNbt(Capability<IMunicipalityManagerCapability> municipalityManagerCapability, IMunicipalityManagerCapability municipalityCapManager) {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put(TAG_MUNICIPALITIES, municipalityCapManager.getMunicipalities().stream().map(IMunicipality::getMunicipalityTag).filter(Objects::nonNull).collect(toListNBT()));
        return compoundTag;
    }

    static void readNbt(Capability<IMunicipalityManagerCapability> municipalityManagerCapability,
                        IMunicipalityManagerCapability instance,
                        Direction side, Tag nbt) {
        final CompoundTag compound = (CompoundTag) nbt;
        for (final Tag tag : compound.getList(TAG_MUNICIPALITIES, Tag.TAG_COMPOUND)) {
            final IMunicipality municipality = Municipality.load((CompoundTag) tag, null);
            if (municipality != null) {
                instance.addMunicipality(municipality);
            }
        }

    }

    static Collector<CompoundTag, ?, ListTag> toListNBT() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    final ListTag tagList = new ListTag();
                    tagList.addAll(list);

                    return tagList;
                });
    }

    Municipality createMunicipality(Level level, BlockPos blockPos);

    List<IMunicipality> getMunicipalities();

    IMunicipality getPlayerInAnyMunicipality(Player player);

    void addMunicipality(IMunicipality municipality);
}

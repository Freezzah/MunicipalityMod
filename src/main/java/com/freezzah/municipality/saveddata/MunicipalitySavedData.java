package com.freezzah.municipality.saveddata;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MunicipalitySavedData extends SavedData {
    private static final String TAG_MUNICIPALITIES = "municipalities";
    private static final String FILE_NAME = new ResourceLocation(Constants.MOD_ID, "municipalities").toString()
            .replace(':', '_');
    private final List<Municipality> municipalities = new ArrayList<>();

    public MunicipalitySavedData() {
        this.setDirty();
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

    static @NotNull MunicipalitySavedData load(@NotNull CompoundTag compoundTag) {
        MunicipalitySavedData savedData = new MunicipalitySavedData();
        for (final Tag tag : compoundTag.getList(TAG_MUNICIPALITIES, Tag.TAG_COMPOUND)) {
            final Municipality municipality = Municipality.load((CompoundTag) tag);
            if (municipality != null) {
                savedData.municipalities.add(municipality);
            }
        }
        return savedData;
    }

    public static @NotNull MunicipalitySavedData get(@NotNull DimensionDataStorage dimensionDataStorage) {
        return dimensionDataStorage.computeIfAbsent(new SavedData.Factory<>(MunicipalitySavedData::new, MunicipalitySavedData::load,
                DataFixTypes.LEVEL), FILE_NAME);
    }

    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        Stream<CompoundTag> tags = municipalities.stream().map(Municipality::getMunicipalityTag);
        compoundTag.put(TAG_MUNICIPALITIES, tags.filter(Objects::nonNull).collect(toListNBT()));
        return compoundTag;
    }

    public List<Municipality> getMunicipalities() {
        return this.municipalities;
    }

    public void add(Municipality municipality) {
        municipalities.add(municipality);
        setDirty();
    }
}
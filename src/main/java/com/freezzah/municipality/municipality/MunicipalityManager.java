package com.freezzah.municipality.municipality;

import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.saveddata.MunicipalitySavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class MunicipalityManager {

    private final ServerLevel level;
    private MunicipalitySavedData savedData;

    public MunicipalityManager(ServerLevel level) {
        this.level = level;
        this.savedData = MunicipalitySavedData.get(level.getDataStorage());
    }

    public @Nullable Municipality createMunicipalityWithPlayer(@NotNull BlockPos blockPos, @NotNull Player player, @NotNull String townhallName) {
        if (this.existMunicipalityAtBlock(blockPos) || this.existsPlayerInAnyMunicipality(player)) {
            // Exist, don't create
            return null;
        }

        if (existMunicipalityWithName(townhallName)) {
            // Exist, don't create
            return null;
        }
        Municipality municipality = new Municipality(UUID.randomUUID(), blockPos);
        municipality.setMunicipalityName(townhallName);
        municipality.setOwner(Inhabitant.fromPlayer(player));
        this.savedData.add(municipality);
        refresh();
        return municipality;
    }

    private boolean existMunicipalityWithName(String townhallName) {
        return getMunicipalities().stream().anyMatch(m -> m.getMunicipalityName().equals(townhallName));
    }

    public @NotNull List<Municipality> getMunicipalities() {
        return this.savedData.getMunicipalities();
    }

    public @Nullable Municipality getMunicipalityByInhabitant(@NotNull Inhabitant inhabitant) {
        return getMunicipalities().stream().filter(m -> m.getInhabitants().stream().anyMatch(p -> p.equals(inhabitant))).findFirst().orElse(null);
    }

    public boolean existsPlayerInAnyMunicipality(@NotNull Player player) {
        return getMunicipalityByInhabitant(Inhabitant.fromPlayer(player)) != null;
    }

    public boolean existMunicipalityAtBlock(@NotNull BlockPos pos) {
        return getMunicipalities().stream().anyMatch(mun -> mun.getTownhallBlockPos().equals(pos));
    }

    public @Nullable Municipality getMunicipalityByBlockPos(@NotNull BlockPos pos) {
        Municipality result = getMunicipalities().stream().filter(municipality -> municipality.getTownhallBlockPos().equals(pos)).findFirst().orElse(null);
        if (result != null) {
            return result;
        }
        return getMunicipalities().stream().filter(municipality -> municipality.existBuildingAtPos(pos)).findFirst().orElse(null);
    }

    void refresh() {
        this.savedData = MunicipalitySavedData.get(level.getDataStorage());
    }
}

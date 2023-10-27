package com.freezzah.municipality.caps;

import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MunicipalityManagerCapability implements IMunicipalityManagerCapability {
    private final List<Municipality> municipalities = new ArrayList<>();

    @Nullable
    @Override
    public Municipality createMunicipalityWithPlayer(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull String townhallName) {
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
        municipalities.add(municipality);
        municipality.setOwner(Inhabitant.fromPlayer(player));

        return municipality;
    }

    private boolean existMunicipalityWithName(String townhallName) {
        return getMunicipalities().stream().anyMatch(m -> m.getMunicipalityName().equals(townhallName));
    }

    @Override
    public @NotNull List<Municipality> getMunicipalities() {
        return this.municipalities;
    }

    @Override
    public @Nullable Municipality getMunicipalityByInhabitant(@NotNull Inhabitant inhabitant) {
        return getMunicipalities().stream().filter(m -> m.getInhabitants().stream().anyMatch(p -> p.equals(inhabitant))).findFirst().orElse(null);
    }

    @Override
    public boolean existsPlayerInAnyMunicipality(@NotNull Player player) {
        return getMunicipalityByInhabitant(Inhabitant.fromPlayer(player)) != null;
    }

    @Override
    public boolean existMunicipalityAtBlock(@NotNull BlockPos pos) {
        return municipalities.stream().anyMatch(mun -> mun.getTownhallBlockPos().equals(pos));
    }

    @Override
    public void addMunicipality(@NotNull Municipality municipality) {
        municipalities.add(municipality);
    }

    @Override
    public @Nullable Municipality getMunicipalityByBlockPos(@NotNull BlockPos pos) {
        return municipalities.stream().filter(municipality -> municipality.getTownhallBlockPos().equals(pos)).findFirst().orElse(null);
    }
}

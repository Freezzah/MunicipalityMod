package com.freezzah.municipality.caps;

import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.IMunicipality;
import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MunicipalityManagerCapability implements IMunicipalityManagerCapability {
    private final List<IMunicipality> municipalities = new ArrayList<>();

    @Override
    public Municipality createMunicipalityWithPlayer(Level level, BlockPos blockPos, Player player, String townhallName) {
        if(this.existMunicipalityAtBlock(blockPos) || this.getPlayerInAnyMunicipality(player) != null){
            // Exist, don't create
            return null;
        }

        if(existMunicipalityWithName(townhallName)){
            // Exist, don't create
            return null;
        }
        Municipality municipality = new Municipality(UUID.randomUUID(), level, blockPos);
        municipality.setMunicipalityName(townhallName);
        municipalities.add(municipality);
        municipality.setOwner(Inhabitant.fromPlayer(player));

        return municipality;
    }

    private boolean existMunicipalityWithName(String townhallName) {
        return getMunicipalities().stream().anyMatch(m -> m.getMunicipalityName().equals(townhallName));
    }

    @Override
    public List<IMunicipality> getMunicipalities() {
        return this.municipalities;
    }

    @Override
    public IMunicipality getInhabitantInAnyMunicipality(Inhabitant inhabitant) {
        return getMunicipalities().stream().filter(m -> m.getInhabitants().stream().anyMatch(p -> p.equals(inhabitant))).findFirst().orElse(null);
    }

    @Override
    public IMunicipality getPlayerInAnyMunicipality(Player player) {
        return getInhabitantInAnyMunicipality(Inhabitant.fromPlayer(player));
    }

    @Override
    public boolean existMunicipalityAtBlock(BlockPos pos){
        return municipalities.stream().anyMatch(mun -> mun.getTownhallBlockPos().equals(pos));
    }

    @Override
    public void addMunicipality(IMunicipality municipality) {
        municipalities.add(municipality);
    }
}
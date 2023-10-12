package com.freezzah.municipality.caps;

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
    public Municipality createMunicipality(Level level, BlockPos blockPos) {
        Municipality municipality = new Municipality(UUID.randomUUID(), level, blockPos);
        municipalities.add(municipality);
        return municipality;
    }

    @Override
    public List<IMunicipality> getMunicipalities() {
        return this.municipalities;
    }

    @Override
    public IMunicipality getPlayerInAnyMunicipality(Player player) {
        for (IMunicipality m : getMunicipalities()) {
            for (Player p : m.getPlayers()) {
                if (p.equals(player)) {
                    return m;
                }
            }
        }
        return null;
    }

    @Override
    public void addMunicipality(IMunicipality municipality) {
        municipalities.add(municipality);
    }
}

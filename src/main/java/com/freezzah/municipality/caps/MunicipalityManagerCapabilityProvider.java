package com.freezzah.municipality.caps;

import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.freezzah.municipality.MunicipalityMod.MUNICIPALITY_MANAGER_CAPABILITY;

public class MunicipalityManagerCapabilityProvider implements ICapabilitySerializable<Tag> {

    private final IMunicipalityManagerCapability municipalityCapManager;
    private final LazyOptional<IMunicipalityManagerCapability> municipalityCapManagerOptional;

    public MunicipalityManagerCapabilityProvider() {
        this.municipalityCapManager = new MunicipalityManagerCapability();
        this.municipalityCapManagerOptional = LazyOptional.of(() -> municipalityCapManager);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == MUNICIPALITY_MANAGER_CAPABILITY ? municipalityCapManagerOptional.cast() : LazyOptional.empty();
    }

    @Override
    public Tag serializeNBT() {
        return IMunicipalityManagerCapability.writeNbt(MUNICIPALITY_MANAGER_CAPABILITY, municipalityCapManager);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        IMunicipalityManagerCapability.readNbt(MUNICIPALITY_MANAGER_CAPABILITY, municipalityCapManager, null, nbt);
    }
}

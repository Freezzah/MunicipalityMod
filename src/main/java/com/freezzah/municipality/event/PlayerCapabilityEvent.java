package com.freezzah.municipality.event;

import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerCapabilityEvent {
    @SubscribeEvent
    public void onRegisterCapabilities(@NotNull RegisterCapabilitiesEvent event) {
        // Register the PlayerMana capability class
        event.register(IMunicipalityManagerCapability.class);
    }
}

package com.freezzah.municipality.event;

import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerCapabilityEvent {
    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        // Register the PlayerMana capability class
        event.register(IMunicipalityManagerCapability.class);
        System.out.println("x");
    }
}

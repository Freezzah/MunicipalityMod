package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import com.freezzah.municipality.network.packet.OpenTownhallViewPacket;
import com.freezzah.municipality.network.packet.OpenVacantTownhallViewPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Constants.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register(IEventBus eventBus) {
        int id = 0;
        INSTANCE.messageBuilder(OpenVacantTownhallViewPacket.class, ++id)
                .encoder(OpenVacantTownhallViewPacket::encode)
                .decoder(OpenVacantTownhallViewPacket::decode)
                .consumerMainThread(OpenVacantTownhallViewPacket::handle).add();
        INSTANCE.messageBuilder(CreateTownhallPacket.class, ++id)
                .encoder(CreateTownhallPacket::encode)
                .decoder(CreateTownhallPacket::decode)
                .consumerMainThread(CreateTownhallPacket::handle).add();
        INSTANCE.messageBuilder(OpenTownhallViewPacket.class, ++id)
                .encoder(OpenTownhallViewPacket::encode)
                .decoder(OpenTownhallViewPacket::decode)
                .consumerMainThread(OpenTownhallViewPacket::handle).add();
    }
}

package com.freezzah.municipality.network.handler;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.network.packet.CreateTownhallPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public class ModPacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
                    new ResourceLocation(Constants.MOD_ID, "main"))
            .simpleChannel();

    public static void register() {
        int id = 0;
        INSTANCE.messageBuilder(CreateTownhallPacket.class, ++id)
                .encoder(CreateTownhallPacket::encode)
                .decoder(CreateTownhallPacket::decode)
                .consumerMainThread(CreateTownhallPacket::handle).add();
    }
}

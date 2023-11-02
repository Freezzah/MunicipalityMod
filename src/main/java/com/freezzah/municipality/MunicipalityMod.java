package com.freezzah.municipality;

import com.freezzah.municipality.blocks.ModBlock;
import com.freezzah.municipality.client.gui.menu.ModMenuType;
import com.freezzah.municipality.event.EventHandler;
import com.freezzah.municipality.event.ModEventHandler;
import com.freezzah.municipality.items.ModItem;
import com.freezzah.municipality.network.handler.ModPacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;

import static com.freezzah.municipality.Constants.MOD_ID;
import static com.mojang.text2speech.Narrator.LOGGER;

@Mod(MOD_ID)
public class MunicipalityMod {

    public MunicipalityMod() {
        LOGGER.info("Municipality: Starting loading");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("Municipality: Registering listeners");
        FMLJavaModLoadingContext.get().getModEventBus().register(new ModEventHandler());
        NeoForge.EVENT_BUS.register(new EventHandler());
        modEventBus.addListener(EventHandler::clientSetup);


        LOGGER.info("Municipality: Registring items");
        ModItem.register(modEventBus);
        LOGGER.info("Municipality: Registring blocks");
        ModBlock.register(modEventBus);
        LOGGER.info("Municipality: Registring menu types");
        ModMenuType.register(modEventBus);
        LOGGER.info("Municipality: Registring packet handler");
        ModPacketHandler.register();

        LOGGER.info("Municipality: Done registring");
    }
}
package com.freezzah.municipality;

import com.freezzah.municipality.blocks.ModBlock;
import com.freezzah.municipality.blocks.entity.ModBlockEntity;
import com.freezzah.municipality.caps.IMunicipalityManagerCapability;
import com.freezzah.municipality.client.gui.menu.ModMenuType;
import com.freezzah.municipality.event.EventHandler;
import com.freezzah.municipality.event.ModEventHandler;
import com.freezzah.municipality.event.PlayerCapabilityEvent;
import com.freezzah.municipality.items.ModItem;
import com.freezzah.municipality.network.handler.ModPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.freezzah.municipality.Constants.MOD_ID;
import static com.mojang.text2speech.Narrator.LOGGER;

@Mod(MOD_ID)
public class MunicipalityMod {
    public static final Capability<IMunicipalityManagerCapability> MUNICIPALITY_MANAGER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public MunicipalityMod() {
        LOGGER.info("Municipality: Starting loading");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("Municipality: Registering listeners");
        FMLJavaModLoadingContext.get().getModEventBus().register(new ModEventHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new PlayerCapabilityEvent());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        modEventBus.addListener(EventHandler::clientSetup);


        LOGGER.info("Municipality: Registring items");
        ModItem.register(modEventBus);
        LOGGER.info("Municipality: Registring blocks");
        ModBlock.register(modEventBus);
        LOGGER.info("Municipality: Registring menu types");
        ModMenuType.register(modEventBus);
        LOGGER.info("Municipality: Registring entities");
        ModBlockEntity.register(modEventBus);
        LOGGER.info("Municipality: Registring packet handler");
        ModPacketHandler.register();

        LOGGER.info("Municipality: Done registring");
    }
}
package com.freezzah.municipality.event;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.blocks.IMunicipalityBlock;
import com.freezzah.municipality.caps.MunicipalityManagerCapabilityProvider;
import com.freezzah.municipality.client.gui.menu.ModMenuType;
import com.freezzah.municipality.client.gui.screen.TownhallScreen;
import com.freezzah.municipality.client.gui.screen.UnclaimedTownhallScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.mojang.text2speech.Narrator.LOGGER;

public class EventHandler {

    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuType.TOWNHALL_MENU.get(), TownhallScreen::new);
            MenuScreens.register(ModMenuType.UNCLAIMED_TOWNHALL_MENU.get(), UnclaimedTownhallScreen::new);
        });
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        //TODO REDO THIS ENTIRE THING
    }

    @SubscribeEvent
    public void onPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        boolean shouldCancel = false;
        if (!event.getLevel().isClientSide()) {
            Block block = event.getState().getBlock();
            if (event.getEntity() instanceof Player) {
                if (block instanceof IMunicipalityBlock) {
                    shouldCancel = true;
                }
            }
        }
        if (event.isCancelable() && shouldCancel) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void attachCapabilityEvent(AttachCapabilitiesEvent<Level> event) {
        if (event.getObject().dimension().equals(Level.OVERWORLD)) {
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "municipality"), new MunicipalityManagerCapabilityProvider());

        } else {
            LOGGER.info("Not overworld");
        }
    }
}

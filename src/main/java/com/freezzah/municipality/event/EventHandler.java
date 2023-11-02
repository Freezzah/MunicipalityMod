package com.freezzah.municipality.event;

import com.freezzah.municipality.blocks.BankBlock;
import com.freezzah.municipality.blocks.IMunicipalityBlock;
import com.freezzah.municipality.client.gui.menu.ModMenuType;
import com.freezzah.municipality.client.gui.screen.TownhallScreen;
import com.freezzah.municipality.client.gui.screen.UnclaimedTownhallScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.jetbrains.annotations.NotNull;

public class EventHandler {

    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuType.TOWNHALL_MENU.get(), TownhallScreen::new);
            MenuScreens.register(ModMenuType.UNCLAIMED_TOWNHALL_MENU.get(), UnclaimedTownhallScreen::new);
        });
    }

    @SubscribeEvent
    public void onBlockBreak(@NotNull BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        if (block instanceof IMunicipalityBlock iMunicipalityBlock) {
            boolean result = iMunicipalityBlock.onBreak(event.getPos(), event.getLevel(), event.getState(), event.getPlayer());
            if (!result) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlaceEvent(@NotNull BlockEvent.EntityPlaceEvent event) {
        boolean shouldCancel = false;
        if (!event.getLevel().isClientSide()) {
            Block block = event.getState().getBlock();
            if (event.getEntity() instanceof ServerPlayer)
                if (block instanceof BankBlock bank) {
                    if (event.getLevel() instanceof Level)
                        if (event.getEntity() instanceof Player)
                            shouldCancel = bank.onPlaceBy((Player) event.getEntity(), event.getPlacedBlock().getBlock(), (Level) event.getLevel(), event.getPos());
                }
        }

        if (shouldCancel) {
            event.setCanceled(true);
        }
    }

//    @SubscribeEvent
//    public void attachCapabilityEvent(@NotNull AttachCapabilitiesEvent<Level> event) {
//        if (event.getObject().dimension().equals(Level.OVERWORLD)) {
//            event.addCapability(new ResourceLocation(Constants.MOD_ID, "municipality"), new MunicipalityManagerCapabilityProvider());
//
//        } else {
//            LOGGER.info("Not overworld");
//        }
//    }
}

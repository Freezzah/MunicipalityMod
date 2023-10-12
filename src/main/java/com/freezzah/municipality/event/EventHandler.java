package com.freezzah.municipality.event;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.blocks.IMunicipalityBlock;
import com.freezzah.municipality.blocks.TownhallBlock;
import com.freezzah.municipality.blocks.entity.TownHallBlockEntity;
import com.freezzah.municipality.client.Localization;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.mojang.text2speech.Narrator.LOGGER;

public class EventHandler {

    private static void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Municipality: Registring menus");
//        event.enqueueWork(
//                () -> MenuScreens.register(ModMenuType.TOWNHALL_MENU.get(), TownhallScreen::new)
//        );
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getLevel().isClientSide()) {
            BlockPos pos = event.getPos();
            Block block = event.getState().getBlock();
            //If Townhall Block
            if (block instanceof TownhallBlock) {
                TownHallBlockEntity townHallBlockEntity = (TownHallBlockEntity) event.getLevel().getBlockEntity(pos);
                //If owner of townhall block
                if (townHallBlockEntity.getOwner() != event.getPlayer()) {
                    if (!townHallBlockEntity.hasActiveMunicipality()) {
                        //Break is safe, dont cancel.
                    } else {
                        event.getPlayer().sendSystemMessage(Component.literal(Localization.ERROR_TOWNHALL_BREAK_ACTIVE_MUNICIPALITY));
                        if (event.isCancelable()) event.setCanceled(true);
                    }
                } else {
                    event.getPlayer().sendSystemMessage(Component.literal(Localization.ERROR_TOWNHALL_NOT_OWNER));
                    if (event.isCancelable()) event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        boolean shouldCancel = false;
        if (!event.getLevel().isClientSide()) {
            BlockPos pos = event.getPos();
            Block block = event.getState().getBlock();
            if (event.getEntity() instanceof Player) {
                if (block instanceof IMunicipalityBlock iMunicipalityBlock) {

                    shouldCancel = false; //todo
                }
            }
        }
        if (event.isCancelable() && shouldCancel) {
            event.setCanceled(true);
        }
    }
}

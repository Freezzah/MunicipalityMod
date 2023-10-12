package com.freezzah.municipality.event;

import com.freezzah.municipality.blocks.ModBlock;
import com.freezzah.municipality.items.ModItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventHandler {
    @SubscribeEvent
    public void createTabBuildContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItem.TOWNHALL_BLOCK_ITEM);
            event.accept(ModBlock.TOWNHALL_BLOCK);
            event.accept(ModItem.TOWNHALL_KEY_ITEM);
        }
    }

}

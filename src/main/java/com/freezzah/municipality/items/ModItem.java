package com.freezzah.municipality.items;

import com.freezzah.municipality.blocks.ModBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import static com.freezzah.municipality.Constants.MOD_ID;

public class ModItem {

    /*
     * Registry for Items.
     */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    /*
     * All items added by this mod.
     */
    public static final RegistryObject<Item> TOWNHALL_BLOCK_ITEM =
            ITEMS.register(ModItemId.TOWNHALL_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.TOWNHALL_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> BANK_BLOCK_ITEM =
            ITEMS.register(ModItemId.BANK_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.BANK_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOWNHALL_KEY_ITEM =
            ITEMS.register(ModItemId.TOWNHALL_KEY_ITEM_ID, MunicipalityKey::new);

    /*
     * Function to call from mod init phase to register all items
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

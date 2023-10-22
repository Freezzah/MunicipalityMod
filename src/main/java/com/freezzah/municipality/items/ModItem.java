package com.freezzah.municipality.items;

import com.freezzah.municipality.blocks.ModBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.freezzah.municipality.Constants.MOD_ID;

public class ModItem {
    /*
     * Object for default item properties
     */
    public static final Item.Properties defaultItemProperties = new Item.Properties();

    /*
     * Registry for Items.
     */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    /*
     * All items added by this mod.
     */
    public static RegistryObject<Item> TOWNHALL_BLOCK_ITEM =
            ITEMS.register(ModItemId.TOWNHALL_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.TOWNHALL_BLOCK.get(), new Item.Properties()));

    public static RegistryObject<Item> TOWNHALL_KEY_ITEM =
            ITEMS.register(ModItemId.TOWNHALL_KEY_ITEM_ID, MunicipalityKey::new);

    /*
     * Function to call from mod init phase to register all items
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

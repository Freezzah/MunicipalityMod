package com.freezzah.municipality.client.gui.menu;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.freezzah.municipality.Constants.MOD_ID;

public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name) {
        return MENUS.register(name, () -> new MenuType(TownhallMenu::new, FeatureFlags.DEFAULT_FLAGS));
    }    public static final RegistryObject<MenuType<TownhallMenu>> TOWNHALL_MENU =
            registerMenuType(ModMenuId.TOWNHALL_MENU_ID);

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }


}

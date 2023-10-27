package com.freezzah.municipality.client.gui.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.municipality.Constants.MOD_ID;

public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

    public static void register(@NotNull IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static final RegistryObject<MenuType<TownhallMenu>> TOWNHALL_MENU = MENUS.register(ModMenuId.TOWNHALL_MENU_ID, () -> IForgeMenuType.create(TownhallMenu::new));
    public static final RegistryObject<MenuType<UnclaimedTownhallMenu>> UNCLAIMED_TOWNHALL_MENU = MENUS.register(ModMenuId.UNCLAIMED_TOWNHALL_MENU_ID, () -> IForgeMenuType.create(UnclaimedTownhallMenu::new));
}

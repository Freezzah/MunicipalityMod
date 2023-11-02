package com.freezzah.municipality.client.gui.menu;

import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.municipality.Constants.MOD_ID;

public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

    public static void register(@NotNull IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static final RegistryObject<MenuType<TownhallMenu>> TOWNHALL_MENU = MENUS.register(ModMenuId.TOWNHALL_MENU_ID, () -> IMenuTypeExtension.create(TownhallMenu::new));
    public static final RegistryObject<MenuType<UnclaimedTownhallMenu>> UNCLAIMED_TOWNHALL_MENU = MENUS.register(ModMenuId.UNCLAIMED_TOWNHALL_MENU_ID, () -> IMenuTypeExtension.create(UnclaimedTownhallMenu::new));
    public static final RegistryObject<MenuType<BankMenu>> BANK_MENU = MENUS.register(ModMenuId.BANK_MENU_ID, () -> IMenuTypeExtension.create(BankMenu::new));

}

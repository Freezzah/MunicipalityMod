package com.freezzah.municipality.blocks;

import com.freezzah.municipality.client.Localization;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.municipality.Constants.MOD_ID;

public class ModBlock {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final @NotNull RegistryObject<TownhallBlock> TOWNHALL_BLOCK =
            BLOCKS.register(ModBlockId.TOWNHALL_BLOCK_ID, () -> new TownhallBlock(Localization.SCREEN_TOWNHALL_NAME));
    public static final @NotNull RegistryObject<BankBlock> BANK_BLOCK =
            BLOCKS.register(ModBlockId.BANK_BLOCK_ID, () -> new BankBlock(Localization.SCREEN_BANK_NAME));

    public static void register(@NotNull IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

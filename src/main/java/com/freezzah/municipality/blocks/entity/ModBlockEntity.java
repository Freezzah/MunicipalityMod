package com.freezzah.municipality.blocks.entity;

import com.freezzah.municipality.blocks.ModBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.freezzah.municipality.Constants.MOD_ID;


public class ModBlockEntity {
    /*
     * Registry for Blocks.
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    /*
     * Function to call from mod init phase to register all Blocks
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }    /*
     * All Blocks added by this mod.
     */
    public static RegistryObject<BlockEntityType<TownHallBlockEntity>> TOWNHALL_BLOCK_ENTITY =
            BLOCKS.register(ModBlockEntityId.TOWNHALL_BLOCK_ENTITY_ID, () -> BlockEntityType.Builder.of(
                    TownHallBlockEntity::new, ModBlock.TOWNHALL_BLOCK.get()).build(null)
            );
    public static RegistryObject<BlockEntityType<BankBlockEntity>> BANK_BLOCK_ENTITY =
            BLOCKS.register(ModBlockEntityId.BANK_BLOCK_ENTITY_ID, () -> BlockEntityType.Builder.of(
                    BankBlockEntity::new, ModBlock.BANK_BLOCK.get()).build(null)
            );


}

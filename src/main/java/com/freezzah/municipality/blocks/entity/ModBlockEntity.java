package com.freezzah.municipality.blocks.entity;

import com.freezzah.municipality.blocks.ModBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.municipality.Constants.MOD_ID;


public class ModBlockEntity {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static void register(@NotNull IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    @SuppressWarnings("DataFlowIssue") // Recommended by forge
    public static final @NotNull RegistryObject<BlockEntityType<TownHallBlockEntity>> TOWNHALL_BLOCK_ENTITY =
            BLOCKS.register(ModBlockEntityId.TOWNHALL_BLOCK_ENTITY_ID, () -> BlockEntityType.Builder.of(
                    TownHallBlockEntity::new, ModBlock.TOWNHALL_BLOCK.get()).build(null)
            );
}
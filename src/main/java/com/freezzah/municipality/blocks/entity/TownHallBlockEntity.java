package com.freezzah.municipality.blocks.entity;

import com.freezzah.municipality.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TownHallBlockEntity extends MunicipalityBlockEntity {

    private Player owner;

    public TownHallBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        super(ModBlockEntity.TOWNHALL_BLOCK_ENTITY.get(), pos, blockState);
    }

    public @Nullable Player getOwner() {
        return owner;
    }

    private void setOwner(@NotNull Player player) {
        this.owner = player;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        CompoundTag nbt = new CompoundTag();
        if (getOwner() != null) {
            nbt.putUUID("Owner", getOwner().getUUID());
        }
        compoundTag.put(Constants.MOD_ID, nbt);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        CompoundTag nbt = compoundTag.getCompound(Constants.MOD_ID);
        UUID ownerUUID = nbt.getUUID("Owner");
        assert Minecraft.getInstance().level != null;
        Player player = Minecraft.getInstance().level.getPlayerByUUID(ownerUUID);
        if (player != null) {
            //TODO throw?
            this.setOwner(player);
        }
    }
}
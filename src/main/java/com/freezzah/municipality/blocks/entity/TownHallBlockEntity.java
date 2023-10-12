package com.freezzah.municipality.blocks.entity;

import com.freezzah.municipality.municipality.Municipality;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TownHallBlockEntity extends BlockEntity {

    private Player owner;
    private Municipality municipality;

    public TownHallBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.TOWNHALL_BLOCK_ENTITY.get(), pos, blockState);
    }

    public boolean isOwner(Player player) {
        return player.equals(this.owner);
    }

    public Player getOwner() {
        return owner;
    }

    private void setOwner(Player player) {
        this.owner = player;
    }

    public boolean hasActiveMunicipality() {
        return this.municipality != null;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        CompoundTag nbt = new CompoundTag();
//        nbt.putUUID("Owner", getOwner().getUUID());
//        compoundTag.put(Constants.MOD_ID, nbt);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
//        CompoundTag nbt = compoundTag.getCompound(Constants.MOD_ID);
//        UUID ownerUUID = nbt.getUUID("Owner");
//        this.setOwner(Minecraft.getInstance().level.getPlayerByUUID(ownerUUID));
    }
}
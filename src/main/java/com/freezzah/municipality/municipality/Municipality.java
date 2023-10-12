package com.freezzah.municipality.municipality;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.municipality.util.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Municipality implements IMunicipality {
    ///
    /// TAGS
    ///
    private static final String TAG_MUNICIPALITY_ID = "municipalityId";
    private static final String TAG_MUNICIPALITY_NAME = "municipalityName";
    private static final String TAG_TOWNHALL = "townhallBlockPos";
    private static final String TAG_DIMENTION_ID = "dimentionid";
    private final UUID id;
    private final List<Player> players = new ArrayList<>();
    ///
    /// MUNICIPALITY OWNED PROPERTIES
    ///
    private BlockPos townhallBlockPos;
    private String municipalityName;
    private ResourceKey<Level> dimentionId;
    private Player owner;
    ///
    /// LOGIC STUFF
    ///
    private CompoundTag municipalityTag;
    private boolean isDirty = true;


    public Municipality(UUID id, Level world) {
        this.id = id;
        if (world != null) {
            this.dimentionId = world.dimension();
        }
    }

    public Municipality(UUID id, Level world, BlockPos townhallBlockPos) {
        this(id, world);
        this.townhallBlockPos = townhallBlockPos;
    }

    public static IMunicipality load(CompoundTag tag, Level level) {
        try {
            UUID id = tag.getUUID(TAG_MUNICIPALITY_ID);
            Municipality municipality = new Municipality(id, level);
            municipality.read(tag);
            return municipality;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the municipalities");
        }
        return null;
    }

    @Override
    public String getMunicipalityName() {
        return municipalityName;
    }

    @Override
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public void addPlayer(Player player) {
        if (owner == null) {
            owner = player;
        }
        players.add(player);
    }

    public BlockPos getTownhallBlockPos() {
        return townhallBlockPos;
    }

    public ResourceKey<Level> getDimentionId() {
        return dimentionId;
    }

    public UUID getId() {
        return id;
    }

    /////////////////////////
    // NBT stuff
    /////////////////////////

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    public CompoundTag write(CompoundTag nbt) {
        nbt.putUUID(TAG_MUNICIPALITY_ID, id);
        nbt.putString(TAG_MUNICIPALITY_NAME, municipalityName);
        BlockPosHelper.write(nbt, townhallBlockPos, TAG_TOWNHALL);
        nbt.putString(TAG_DIMENTION_ID, dimentionId.location().toString());
        this.municipalityTag = nbt;
        isDirty = false;
        return nbt;
    }

    public void read(CompoundTag nbt) {
        dimentionId = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(nbt.getString(TAG_DIMENTION_ID)));
        townhallBlockPos = BlockPosHelper.read(nbt, TAG_TOWNHALL);
        municipalityName = nbt.getString(TAG_MUNICIPALITY_NAME);
        this.municipalityTag = nbt;
    }

    public void markDirty() {
        this.isDirty = true;
    }

    @Override
    public CompoundTag getMunicipalityTag() {
        try {
            if (this.municipalityTag == null || this.isDirty) {
                this.write(new CompoundTag());
            }
        } catch (final Exception e) {
            Constants.LOGGER.warn("Something went wrong persisting colony: " + id, e);
        }
        return this.municipalityTag;
    }

    @Override
    public boolean isOwner(Player player) {
        return this.owner.equals(player);
    }

    @Override
    public void setOwner(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        this.owner = player;
    }
}

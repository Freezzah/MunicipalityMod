package com.freezzah.municipality.municipality;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.util.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.script.Compilable;
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
    private static final String TAG_OWNER_UUID = "ownerUuid";
    private static final String TAG_OWNER_NAME = "ownerName";
    private static final String TAG_LIST_INHABITANT_UUID = "listPlayerUuid";
    private static final String TAG_INHABITANT_UUID = "playerUuid";
    private static final String TAG_INHABITANT_NAME = "playerName";
    private final UUID id;
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    ///
    /// MUNICIPALITY OWNED PROPERTIES
    ///
    private BlockPos townhallBlockPos;
    private String municipalityName;
    private ResourceKey<Level> dimensionId;
    private Inhabitant owner;
    ///
    /// LOGIC STUFF
    ///
    private CompoundTag municipalityTag;
    private boolean isDirty = true;


    public Municipality(UUID id, Level world) {
        this.id = id;
        if (world != null) {
            this.dimensionId = world.dimension();
        }
        setDirty(true);
    }

    public Municipality(UUID id, Level world, BlockPos townhallBlockPos) {
        this(id, world);
        this.townhallBlockPos = townhallBlockPos;
        setDirty(true);
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
    public List<Inhabitant> getInhabitants() {
        return this.inhabitants;
    }

    @Override
    public void addInhabitant(Inhabitant inhabitant) {
        if (owner == null) {
            owner = inhabitant;
        }
        inhabitants.add(inhabitant);
        setDirty(true);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public BlockPos getTownhallBlockPos() {
        return townhallBlockPos;
    }

    @Override
    public List<Player> getInhabitantsAsPlayers(Level level) {
        List<Player> players = new ArrayList<>();
        for (Inhabitant inhabitant : getInhabitants()){
            players.add(inhabitant.toPlayer(level));
        }
        return players;
    }

    @Override
    public ResourceKey<Level> getDimensionId() {
        return dimensionId;
    }

    @Override
    public Inhabitant getOwner() {
        return owner;
    }

    @Override
    public boolean isOwner(Inhabitant inhabitant) {
        return this.owner.equals(inhabitant);
    }

    @Override
    public void setOwner(Inhabitant inhabitant) {
        if (!inhabitants.contains(inhabitant)) {
            inhabitants.add(inhabitant);
        }
        this.owner = inhabitant;
        setDirty(true);
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
        nbt.putString(TAG_DIMENTION_ID, dimensionId.location().toString());
        nbt.putUUID(TAG_OWNER_UUID, owner.getUUID());
        nbt.putString(TAG_OWNER_NAME, owner.getName());

        ListTag listInhabitantsTag = new ListTag();
        for(int i = 0; i < getInhabitants().size(); i++){
            Inhabitant inhabitant = getInhabitants().get(i);
            CompoundTag inhabitantTag = new CompoundTag();
            inhabitantTag.putUUID(TAG_INHABITANT_UUID, inhabitant.getUUID());
            inhabitantTag.putString(TAG_INHABITANT_NAME, inhabitant.getName());
            listInhabitantsTag.add(i, inhabitantTag);
        }
        nbt.put(TAG_LIST_INHABITANT_UUID, listInhabitantsTag);
        this.municipalityTag = nbt;
        isDirty = false;
        return nbt;
    }

    public void read(CompoundTag nbt) {
        municipalityName = nbt.getString(TAG_MUNICIPALITY_NAME);
        townhallBlockPos = BlockPosHelper.read(nbt, TAG_TOWNHALL);
        dimensionId = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(nbt.getString(TAG_DIMENTION_ID)));
        owner = new Inhabitant(nbt.getUUID(TAG_OWNER_UUID), nbt.getString(TAG_OWNER_NAME));
        ListTag listPlayersTag = nbt.getList(TAG_LIST_INHABITANT_UUID, Tag.TAG_COMPOUND);
        for(int i = 0; i < listPlayersTag.size(); i++){
            CompoundTag tag = listPlayersTag.getCompound(i);
            Inhabitant inhabitant = new Inhabitant(tag.getUUID(TAG_INHABITANT_UUID), tag.getString(TAG_INHABITANT_NAME));
            this.inhabitants.add(inhabitant);
        }
        isDirty = false;
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
}
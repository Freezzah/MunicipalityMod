package com.freezzah.municipality.municipality;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.util.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
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
    private static final String TAG_OWNER_UUID = "ownerUuid";
    private static final String TAG_OWNER_NAME = "ownerName";
    private static final String TAG_LIST_INHABITANT_UUID = "listPlayerUuid";
    private static final String TAG_INHABITANT_UUID = "playerUuid";
    private static final String TAG_INHABITANT_NAME = "playerName";
    private final UUID id;
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    private final int happiness;
    ///
    /// MUNICIPALITY OWNED PROPERTIES
    ///
    private BlockPos townhallBlockPos;
    private String municipalityName;
    private Inhabitant owner;
    ///
    /// LOGIC STUFF
    ///
    private CompoundTag municipalityTag;
    private boolean isDirty = true;


    public Municipality(UUID id) {
        this.id = id;
        this.happiness = 100;
        setDirty(true);
    }

    public Municipality(UUID id, BlockPos townhallBlockPos) {
        this(id);
        this.townhallBlockPos = townhallBlockPos;
        setDirty(true);
    }

    public static IMunicipality load(CompoundTag tag) {
        try {
            UUID id = tag.getUUID(TAG_MUNICIPALITY_ID);
            Municipality municipality = new Municipality(id);
            municipality.read(tag);
            return municipality;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the municipalities");
        }
        return null;
    }

    public static IMunicipality fromFriendlyByteBuf(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        return Municipality.load(tag);
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
    public BlockPos getTownhallBlockPos() {
        return townhallBlockPos;
    }

    @Override
    public List<Player> getInhabitantsAsPlayers(Level level) {
        List<Player> players = new ArrayList<>();
        for (Inhabitant inhabitant : getInhabitants()) {
            players.add(inhabitant.toPlayer(level));
        }
        return players;
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

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CompoundTag write(CompoundTag nbt) {
        nbt.putUUID(TAG_MUNICIPALITY_ID, id);
        nbt.putString(TAG_MUNICIPALITY_NAME, municipalityName);
        BlockPosHelper.write(nbt, townhallBlockPos, TAG_TOWNHALL);
        nbt.putUUID(TAG_OWNER_UUID, owner.getUUID());
        nbt.putString(TAG_OWNER_NAME, owner.getName());

        ListTag listInhabitantsTag = new ListTag();
        for (int i = 0; i < getInhabitants().size(); i++) {
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
        owner = new Inhabitant(nbt.getUUID(TAG_OWNER_UUID), nbt.getString(TAG_OWNER_NAME));
        ListTag listPlayersTag = nbt.getList(TAG_LIST_INHABITANT_UUID, Tag.TAG_COMPOUND);
        for (int i = 0; i < listPlayersTag.size(); i++) {
            CompoundTag tag = listPlayersTag.getCompound(i);
            Inhabitant inhabitant = new Inhabitant(tag.getUUID(TAG_INHABITANT_UUID), tag.getString(TAG_INHABITANT_NAME));
            this.inhabitants.add(inhabitant);
        }
        isDirty = false;
        this.municipalityTag = nbt;
    }

    @Override
    public int getHappiness() {
        return this.happiness;
    }

    @Override
    public FriendlyByteBuf putInByteBuf(FriendlyByteBuf friendlyByteBuf) {
        return friendlyByteBuf.writeNbt(getMunicipalityTag());
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
package com.freezzah.municipality.municipality;

import com.freezzah.municipality.Constants;
import com.freezzah.municipality.blocks.building.BuildingTownhall;
import com.freezzah.municipality.blocks.building.EnumBuilding;
import com.freezzah.municipality.blocks.building.IBuilding;
import com.freezzah.municipality.entity.Inhabitant;
import com.freezzah.municipality.municipality.util.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Municipality {

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
    private static final String TAG_MUNICIPALITY = "municipality";

    ///
    /// NBT related properties
    ///
    private final UUID id;
    private final List<Inhabitant> inhabitants = new ArrayList<>();

    ///
    /// MUNICIPALITY OWNED PROPERTIES
    ///
    private final int happiness;
    private String municipalityName;
    private Inhabitant owner;
    private CompoundTag municipalityTag;
    private boolean isDirty = true;
    private BlockPos townhallBlockPos;

    ///
    /// Manager
    ///
    private BuildingManager buildingManager = new BuildingManager(this);

    public Municipality(UUID id) {
        this.id = id;
        this.happiness = 100;
        setDirty(true);
    }

    public Municipality(UUID id, BlockPos townhallBlockPos) {
        this(id);
        this.townhallBlockPos = townhallBlockPos;
        addBuilding(EnumBuilding.fromByteType(BuildingTownhall.type, this, townhallBlockPos));
        setDirty(true);
    }

    // EXTRACTS MUNICIPALITY WRAPPER
    public static @Nullable Municipality load(CompoundTag tag) {
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

    @Nullable
    public static Municipality fromFriendlyByteBuf(@NotNull FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) {
            return null;
        }
        CompoundTag compoundTag = tag.getCompound(TAG_MUNICIPALITY);
        return Municipality.load(compoundTag);
    }

    public String getMunicipalityName() {
        checkDirty();
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        checkDirty();
        this.municipalityName = municipalityName;
        setDirty(true);
    }

    public List<Inhabitant> getInhabitants() {
        checkDirty();
        return this.inhabitants;
    }

    public List<Player> getInhabitantsAsPlayers(Level level) {
        checkDirty();
        return getInhabitants().stream().map(inhabitant -> inhabitant.toPlayer(level)).collect(Collectors.toList());
    }

    public boolean isOwner(Inhabitant inhabitant) {
        checkDirty();
        return this.owner.equals(inhabitant);
    }

    public void setOwner(Inhabitant inhabitant) {
        checkDirty();
        if (!inhabitants.contains(inhabitant)) {
            inhabitants.add(inhabitant);
        }
        this.owner = inhabitant;
        setDirty(true);
    }

    public int getHappiness() {
        checkDirty();
        return this.happiness;
    }

    public BlockPos getTownhallBlockPos() {
        checkDirty();
        return townhallBlockPos;
    }

    public boolean addBuilding(IBuilding building) {
        checkDirty();
        setDirty(true);
        return this.buildingManager.addBuilding(building);
    }

    /////////////////////////
    // NBT stuff
    /////////////////////////

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    public boolean removeBuilding(@NotNull IBuilding building) {
        setDirty(true);
        return this.buildingManager.removeBuilding(building.getBlockPos());
    }

    public List<IBuilding> getBuildings() {
        return this.buildingManager.getBuildings();
    }

    private void checkDirty() {
        if (isDirty) {
            this.refresh();
        }
    }

    private void refresh() {
        this.write(new CompoundTag());
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull CompoundTag write(@NotNull CompoundTag nbt) {
        CompoundTag compoundTag = new CompoundTag();

        compoundTag.putUUID(TAG_MUNICIPALITY_ID, id);

        // We can have that we create a municipality,
        // but since we cannot set the owner or name we need to abort here
        if (municipalityName == null || owner == null) {
            return nbt;
        }

        compoundTag.putString(TAG_MUNICIPALITY_NAME, municipalityName);
        BlockPosHelper.write(compoundTag, townhallBlockPos, TAG_TOWNHALL);
        compoundTag.putUUID(TAG_OWNER_UUID, owner.getUUID());
        compoundTag.putString(TAG_OWNER_NAME, owner.getName());

        ListTag listInhabitantsTag = new ListTag();
        for (int i = 0; i < this.inhabitants.size(); i++) { //Do not use getInhabitants here, as it will check dirt
            Inhabitant inhabitant = this.inhabitants.get(i);
            CompoundTag inhabitantTag = new CompoundTag();
            inhabitantTag.putUUID(TAG_INHABITANT_UUID, inhabitant.getUUID());
            inhabitantTag.putString(TAG_INHABITANT_NAME, inhabitant.getName());
            listInhabitantsTag.add(i, inhabitantTag);
        }
        compoundTag.put(TAG_LIST_INHABITANT_UUID, listInhabitantsTag);
        buildingManager.save(compoundTag);
        nbt.put(TAG_MUNICIPALITY, compoundTag);

        this.municipalityTag = compoundTag;
        isDirty = false;
        return nbt;
    }

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

    /// SHOULD HAVE ALREADY EXTRACTED THE MUNICIPALITY WRAPPER
    public void read(@NotNull CompoundTag nbt) {
        municipalityName = nbt.getString(TAG_MUNICIPALITY_NAME);
        townhallBlockPos = BlockPosHelper.read(nbt, TAG_TOWNHALL);
        owner = new Inhabitant(nbt.getUUID(TAG_OWNER_UUID), nbt.getString(TAG_OWNER_NAME));
        ListTag listPlayersTag = nbt.getList(TAG_LIST_INHABITANT_UUID, Tag.TAG_COMPOUND);
        for (int i = 0; i < listPlayersTag.size(); i++) {
            CompoundTag tag = listPlayersTag.getCompound(i);
            Inhabitant inhabitant = new Inhabitant(tag.getUUID(TAG_INHABITANT_UUID), tag.getString(TAG_INHABITANT_NAME));
            this.inhabitants.add(inhabitant);
        }
        this.buildingManager = BuildingManager.load(nbt, this);
        setDirty(false);
        this.municipalityTag = nbt;
    }

    public FriendlyByteBuf putInFriendlyByteBuf(@NotNull FriendlyByteBuf friendlyByteBuf) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put(TAG_MUNICIPALITY, getMunicipalityTag());
        return friendlyByteBuf.writeNbt(compoundTag);
    }
}
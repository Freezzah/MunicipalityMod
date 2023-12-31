package com.freezzah.municipality.municipality;

import com.freezzah.municipality.blocks.building.EnumBuilding;
import com.freezzah.municipality.blocks.building.IBuilding;
import com.freezzah.municipality.municipality.util.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingManager {
    private static final String TAG_BUILDING = "building";
    private static final String TAG_BUILDING_POS = "buildingPos";
    private static final String TAG_BUILDING_TYPE = "buildingType";
    private static final String TAG_BUILDING_MANAGER = "buildingManager";
    private final Municipality municipality;
    private final Map<BlockPos, IBuilding> buildings = new HashMap<>();

    public BuildingManager(@NotNull Municipality municipality) {
        this.municipality = municipality;
    }

    public static @Nullable BuildingManager load(@NotNull CompoundTag tag, @NotNull Municipality municipality) {
        try {
            BuildingManager buildingManager = new BuildingManager(municipality);
            buildingManager.read(tag);
            return buildingManager;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addBuilding(@NotNull IBuilding building) {
        if (buildings.containsKey(building.getBlockPos())) {
            return false;
        } else {
            buildings.put(building.getBlockPos(), building);
            return true;
        }
    }

    public boolean removeBuilding(@NotNull BlockPos pos) {
        buildings.remove(pos);
        return true;
    }

    public @NotNull List<IBuilding> getBuildings() {
        return this.buildings.values().stream().toList();
    }


    public boolean existBuildingAtPos(@NotNull BlockPos blockPos) {
        return buildings.keySet().stream().anyMatch(pos -> pos.equals(blockPos));
    }

    @Nullable
    public IBuilding findBuildingAtPos(@NotNull BlockPos blockPos) {
        BlockPos resultPos = buildings.keySet().stream().filter(pos -> pos.equals(blockPos)).findFirst().orElse(null);
        return resultPos != null ? buildings.get(resultPos) : null;
    }

    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        CompoundTag buildingManager = new CompoundTag();
        ListTag buildings = new ListTag();
        int i = 0;
        for (BlockPos key : this.buildings.keySet()) {
            IBuilding building = this.buildings.get(key);

            CompoundTag buildingTag = new CompoundTag();
            buildingTag.putByte(TAG_BUILDING_TYPE, building.getType());
            BlockPosHelper.write(buildingTag, building.getBlockPos(), TAG_BUILDING_POS);
            buildingTag = building.serializeNBT(buildingTag);

            buildings.add(i, buildingTag);
            i++;
        }
        buildingManager.put(TAG_BUILDING, buildings);
        tag.put(TAG_BUILDING_MANAGER, buildingManager);
        return tag;
    }

    public void read(@NotNull CompoundTag tag) {
        ListTag buildings = tag.getList(TAG_BUILDING, Tag.TAG_COMPOUND);
        for (int i = 0; i < buildings.size(); i++) {
            CompoundTag compoundTag = buildings.getCompound(i);
            BlockPos pos = BlockPosHelper.read(compoundTag, TAG_BUILDING_POS);
            if (pos != null) {
                IBuilding building = EnumBuilding.fromByteType(compoundTag.getByte(TAG_BUILDING_TYPE), municipality, pos);
                if (building == null) {
                    continue;
                }
                building.deserializeNBT(compoundTag);
                this.addBuilding(building);
            }
        }
    }
}

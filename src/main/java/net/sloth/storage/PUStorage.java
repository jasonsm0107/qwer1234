package net.sloth.storage;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class PUStorage {
    private Map<ResourceLocation, List<BlockPos>> knownLootBalls;

    public Map<ResourceLocation, List<BlockPos>> getKnownLootBalls() {
        return this.knownLootBalls;
    }

    public void setKnownLootBalls(Map<ResourceLocation, List<BlockPos>> knownLootBalls) {
        this.knownLootBalls = knownLootBalls;
    }

    public void addKnownLootBall(BlockPos pos, ResourceLocation dimension) {
        List<BlockPos> knownPositions;
        if (this.knownLootBalls.containsKey(dimension)) {
            knownPositions = this.knownLootBalls.get(dimension);
        } else {
            knownPositions = new ArrayList<>();
        }

        if (!knownPositions.contains(pos)) {
            knownPositions.add(pos);
        }

        this.knownLootBalls.put(dimension, knownPositions);
    }

    public List<BlockPos> getKnownLootBalls(ResourceLocation dimension) {
        return this.knownLootBalls.containsKey(dimension) ? ImmutableList.copyOf((Collection)this.knownLootBalls.get(dimension)) : ImmutableList.of();
    }

    public boolean knowsLootBall(ResourceLocation dimension, BlockPos pos) {
        if (!this.knownLootBalls.containsKey(dimension)) {
            return false;
        } else {
            List<BlockPos> knownPositions = (List)this.knownLootBalls.get(dimension);
            return knownPositions.isEmpty() ? false : knownPositions.contains(pos);
        }
    }

    public PUStorage(Map<ResourceLocation, List<BlockPos>> knownLootBalls) {
        this.knownLootBalls = knownLootBalls;
    }

    public PUStorage() {
    }
}
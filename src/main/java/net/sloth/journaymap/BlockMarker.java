package net.sloth.journaymap;


import net.sloth.feature.IBlockTarget;
import net.sloth.util.PUJourneyMapUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import journeymap.client.api.display.Waypoint;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMarker {
    private final ValidCheck isValidBlock;
    private final BooleanSupplier isEnabled;
    private final Color color;
    private Map<BlockPos, Waypoint> waypoints = new ConcurrentHashMap();

    public static BlockMarker with(Supplier<IBlockTarget> target) {
        return with((world, pos, state) -> {
            return ((IBlockTarget)target.get()).getValidBlockTest().test(state);
        }, () -> {
            return ((IBlockTarget)target.get()).isEnabled();
        }, ((IBlockTarget)target.get()).getColor());
    }

    public Waypoint add(World world, BlockState state, BlockPos blockPos, Color color) {
        if (this.waypoints.containsKey(blockPos)) {
            return (Waypoint)this.waypoints.get(blockPos);
        } else {
            Waypoint waypoint = PUJourneyMapUtil.createWaypoint(state, world, blockPos, color);
            this.waypoints.put(blockPos, waypoint);
            return waypoint;
        }
    }

    public Collection<Waypoint> getInvalidWaypoints(World world) {
        List<Waypoint> invalid = new ArrayList();
        Iterator var3 = this.getWaypoints().iterator();

        while(var3.hasNext()) {
            Waypoint waypoint = (Waypoint)var3.next();
            BlockPos pos = waypoint.getPosition();
            BlockState state = world.getBlockState(pos);
            if (!this.isValid(world, pos, state)) {
                invalid.add(waypoint);
            }
        }

        return invalid;
    }

    public void remove(BlockPos blockPos) {
        this.waypoints.remove(blockPos);
    }

    public Collection<Waypoint> getWaypoints() {
        return this.waypoints.values();
    }

    public void clear() {
        this.waypoints.clear();
    }

    public boolean isEnabled() {
        return this.isEnabled.getAsBoolean();
    }

    public boolean isValid(World world, BlockPos blockPos, BlockState state) {
        return this.isValidBlock.test(world, blockPos, state);
    }

    private BlockMarker(ValidCheck isValidBlock, BooleanSupplier isEnabled, Color color) {
        this.isValidBlock = isValidBlock;
        this.isEnabled = isEnabled;
        this.color = color;
    }

    public static BlockMarker with(ValidCheck isValidBlock, BooleanSupplier isEnabled, Color color) {
        return new BlockMarker(isValidBlock, isEnabled, color);
    }

    public Color getColor() {
        return this.color;
    }

    @FunctionalInterface
    public interface ValidCheck {
        boolean test(World var1, BlockPos var2, BlockState var3);
    }
}

package net.sloth.util;

import java.awt.Color;
import journeymap.client.api.display.Waypoint;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class PUJourneyMapUtil {
    public static Waypoint createWaypoint(BlockState blockState, World world, BlockPos pos, Color color) {
        return createWaypoint(blockState.getBlock().getName().getString(), world, pos, color);
    }

    public static Waypoint createWaypoint(String name, World world, BlockPos pos, Color color) {
        return createWaypoint(name, world, pos, color.getRGB());
    }

    public static Waypoint createWaypoint(String name, World world, BlockPos pos, int color) {
        Waypoint waypoint = new Waypoint("ingka1111", name, world.dimension(), pos);
        waypoint.setColor(color);
        waypoint.setEditable(false);
        waypoint.setPersistent(false);
        return waypoint;
    }

    private PUJourneyMapUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

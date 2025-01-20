package net.sloth.handler;

import net.sloth.config.PUConfig;
import net.sloth.journaymap.BlockMarker;
import net.sloth.journaymap.EntityMarker;
import net.sloth.journaymap.PUJourneyMapPlugin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import journeymap.client.api.display.Waypoint;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PUTaskHandler {
    private static final Logger log = LogManager.getLogger("pixelmonutils/taskHandler");
    private static ScheduledExecutorService executor;
    private static ScheduledFuture<?> searchTask = null;

    public PUTaskHandler() {
    }

    public static void init() {
        log.debug("Initializing Task Executor");
        executor = Executors.newScheduledThreadPool((Integer)PUConfig.INSTANCE.threadPoolSize.get());
        initSearchTask();
        log.debug("Initialization finished");
    }

    public static void initSearchTask() {
        if (searchTask == null) {
            searchTask = executor.scheduleWithFixedDelay(() -> {
                try {
                    World world = Minecraft.getInstance().level;
                    PlayerEntity player = Minecraft.getInstance().player;
                    if (world != null) {
                        if (player != null) {
                            if (PUJourneyMapPlugin.API != null) {
                                long start = System.nanoTime();
                                log.debug("Start searching Task with {} total markers", PUJourneyMapPlugin.ENTITY_MARKERS.size() + PUJourneyMapPlugin.BLOCK_MARKERS.size());
                                List<Waypoint> invalidWaypoints = new ArrayList();
                                List<Waypoint> addedWaypoints = new ArrayList();
                                int seekRange = (Integer)PUConfig.INSTANCE.scanRadius.get();
                                Iterator var7 = PUJourneyMapPlugin.BLOCK_MARKERS.iterator();

                                while(true) {
                                    while(var7.hasNext()) {
                                        BlockMarker markerx = (BlockMarker)var7.next();
                                        if (!markerx.isEnabled()) {
                                            if (!markerx.getWaypoints().isEmpty()) {
                                                invalidWaypoints.addAll(markerx.getWaypoints());
                                                markerx.clear();
                                            }
                                        } else {
                                            Collection<Waypoint> invalidMarker = markerx.getInvalidWaypoints(world);
                                            invalidWaypoints.addAll(invalidMarker);
                                            invalidMarker.forEach((waypointx) -> {
                                                markerx.remove(waypointx.getPosition());
                                            });
                                            BlockPos playerPos = ((PlayerEntity)player).blockPosition();

                                            for(int x = -seekRange; x < seekRange; ++x) {
                                                for(int z = -seekRange; z < seekRange; ++z) {
                                                    for(int y = -seekRange; y < seekRange; ++y) {
                                                        BlockPos pos = playerPos.offset(x, y, z);
                                                        if (pos.getY() >= 0 && pos.getY() <= 250) {
                                                            BlockState blockState = ((World)world).getBlockState(pos);
                                                            if (markerx.isValid(world, pos, blockState)) {
                                                                addedWaypoints.add(markerx.add(world, blockState, pos, markerx.getColor()));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    BlockPos playerPosx = ((PlayerEntity)player).blockPosition();
                                    AxisAlignedBB searchArea = AxisAlignedBB.of(MutableBoundingBox.createProper(playerPosx.getX() - seekRange, Math.max(0, playerPosx.getY() - seekRange), playerPosx.getZ() - seekRange, playerPosx.getX() + seekRange, Math.min(250, playerPosx.getY() + seekRange), playerPosx.getZ() + seekRange));
                                    Iterator var21 = PUJourneyMapPlugin.ENTITY_MARKERS.iterator();

                                    while(true) {
                                        while(var21.hasNext()) {
                                            EntityMarker marker = (EntityMarker)var21.next();
                                            if (!marker.isEnabled()) {
                                                if (!marker.getWaypoints().isEmpty()) {
                                                    invalidWaypoints.addAll(marker.getWaypoints());
                                                    marker.clear();
                                                }
                                            } else {
                                                invalidWaypoints.addAll(marker.getInvalidWaypoints(world, true));
                                                List<Entity> entitiesInArea = ((World)world).getEntitiesOfClass(marker.getTypeClass(), searchArea, Entity::isAlive);
                                                Iterator var28 = entitiesInArea.iterator();

                                                while(var28.hasNext()) {
                                                    Entity entity = (Entity)var28.next();
                                                    if (marker.isValid(world, entity.blockPosition(), entity)) {
                                                        addedWaypoints.add(marker.add(entity));
                                                    }
                                                }
                                            }
                                        }

                                        Waypoint waypoint;
                                        if (!invalidWaypoints.isEmpty()) {
                                            log.debug("Removing {} Waypoints", invalidWaypoints.size());
                                            var21 = invalidWaypoints.iterator();

                                            while(var21.hasNext()) {
                                                waypoint = (Waypoint)var21.next();
                                                PUJourneyMapPlugin.API.remove(waypoint);
                                            }
                                        }

                                        if (!addedWaypoints.isEmpty()) {
                                            log.debug("Adding {} Waypoints", addedWaypoints.size());
                                            var21 = addedWaypoints.iterator();

                                            while(var21.hasNext()) {
                                                waypoint = (Waypoint)var21.next();

                                                try {
                                                    PUJourneyMapPlugin.API.show(waypoint);
                                                } catch (Exception var16) {
                                                    log.error("Error on showing waypoint.", var16);
                                                }
                                            }
                                        }

                                        long timeDiff = System.nanoTime() - start;
                                        String timeUnit = "ns";
                                        if (timeDiff >= 1000L) {
                                            timeUnit = "μs";
                                            timeDiff /= 1000L;
                                            if (timeDiff >= 1000L) {
                                                timeUnit = "ms";
                                                timeDiff /= 1000L;
                                                if (timeDiff >= 1000L) {
                                                    timeUnit = "s";
                                                    timeDiff /= 1000L;
                                                }
                                            }
                                        }

                                        log.debug("Search task finished in {}{}", timeDiff, timeUnit);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception var17) {
                    Exception e = var17;
                    log.error("Error on executing search task.\n", e);
                }
            }, 0L, (long)(Integer)PUConfig.INSTANCE.updateDelay.get(), TimeUnit.SECONDS);
        }

    }

    public static boolean isSearchTaskRunning() {
        if (searchTask == null) {
            return false;
        } else if (searchTask.isDone()) {
            return false;
        } else {
            return !searchTask.isCancelled();
        }
    }
}

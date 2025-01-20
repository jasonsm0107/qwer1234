package net.sloth.journaymap;


import com.pixelmonmod.pixelmon.entities.pixelmon.AbstractBaseEntity;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.sloth.util.PUJourneyMapUtil;
import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import journeymap.client.api.display.Waypoint;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMarker<T extends Entity> {
    private final Class<T> typeClass;
    private final ValidCheck<T> isValidBlock;
    private final BooleanSupplier isEnabled;
    private final Function<T, String> nameBuilder;
    private final BiConsumer<T, Waypoint> onFoundCallback;
    private final Color color;
    private Map<Integer, Waypoint> waypoints = new ConcurrentHashMap();

    public static <T extends Entity> EntityMarker<T> with(Class<T> pixelmonEntityClass, ValidCheck<T> validCheck, BooleanSupplier isEnabled, Function<T, String> nameBuilder, Color color) {
        return with(pixelmonEntityClass, validCheck, isEnabled, nameBuilder, (t, waypoint) -> {
        }, color);
    }

    public static EntityMarker<PixelmonEntity> withPixelmon(ValidCheck<PixelmonEntity> validCheck, BooleanSupplier isEnabled, Color color) {
        return with(PixelmonEntity.class, validCheck, isEnabled, AbstractBaseEntity::getLocalizedName, color);
    }

    public static EntityMarker<PixelmonEntity> withPixelmon(ValidCheck<PixelmonEntity> validCheck, BooleanSupplier isEnabled, Color color, BiConsumer<PixelmonEntity, Waypoint> onFound) {
        return with(PixelmonEntity.class, validCheck, isEnabled, AbstractBaseEntity::getLocalizedName, onFound, color);
    }

    public Waypoint add(T entity) {
        Waypoint waypoint;
        if (this.waypoints.containsKey(entity.getId())) {
            waypoint = (Waypoint)this.waypoints.get(entity.getId());
            waypoint.setPosition(waypoint.getDimension(), entity.blockPosition());
            this.onFoundCallback.accept(entity, waypoint);
            return waypoint;
        } else {
            waypoint = PUJourneyMapUtil.createWaypoint(this.getName(entity), entity.level, entity.blockPosition(), this.color);
            this.onFoundCallback.accept(entity, waypoint);
            this.waypoints.put(entity.getId(), waypoint);
            return waypoint;
        }
    }

    public Collection<Waypoint> getInvalidWaypoints(World world, boolean remove) {
        Map<Integer, Waypoint> invalid = new HashMap();
        Iterator var4 = this.waypoints.keySet().iterator();

        int entityId;
        while(var4.hasNext()) {
            entityId = (Integer)var4.next();
            Entity entity = world.getEntity(entityId);
            if (entity == null) {
                invalid.put(entityId, this.waypoints.get(entityId));
            } else if (!entity.isAlive()) {
                invalid.put(entityId, this.waypoints.get(entityId));
            } else if (!this.typeClass.isInstance(entity)) {
                invalid.put(entityId, this.waypoints.get(entityId));
            } else if (!this.isValid(world, entity.blockPosition(), this.typeClass.cast(entity))) {
                invalid.put(entityId, this.waypoints.get(entityId));
            }
        }

        if (remove) {
            var4 = invalid.keySet().iterator();

            while(var4.hasNext()) {
                entityId = (Integer)var4.next();
                this.waypoints.remove(entityId);
            }
        }

        return invalid.values();
    }

    public void remove(int entityId) {
        this.waypoints.remove(entityId);
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

    public boolean isValid(World world, BlockPos blockPos, T entity) {
        return this.isValidBlock.test(world, blockPos, entity);
    }

    public String getName(T entity) {
        return (String)this.nameBuilder.apply(entity);
    }

    private EntityMarker(Class<T> typeClass, ValidCheck<T> isValidBlock, BooleanSupplier isEnabled, Function<T, String> nameBuilder, BiConsumer<T, Waypoint> onFoundCallback, Color color) {
        this.typeClass = typeClass;
        this.isValidBlock = isValidBlock;
        this.isEnabled = isEnabled;
        this.nameBuilder = nameBuilder;
        this.onFoundCallback = onFoundCallback;
        this.color = color;
    }

    public static <T extends Entity> EntityMarker<T> with(Class<T> typeClass, ValidCheck<T> isValidBlock, BooleanSupplier isEnabled, Function<T, String> nameBuilder, BiConsumer<T, Waypoint> onFoundCallback, Color color) {
        return new EntityMarker(typeClass, isValidBlock, isEnabled, nameBuilder, onFoundCallback, color);
    }

    public Class<T> getTypeClass() {
        return this.typeClass;
    }

    public Color getColor() {
        return this.color;
    }

    @FunctionalInterface
    public interface ValidCheck<T extends Entity> {
        boolean test(World var1, BlockPos var2, T var3);
    }
}

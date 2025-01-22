package net.sloth;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.sloth.NativeBridge;
import net.sloth.ErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "ingka1111")
public class PUConfig {
    private static final Logger LOGGER = LogManager.getLogger(PUConfig.class);
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    private static final Map<String, Object> DEFAULT_VALUES = new HashMap<>();

    // Feature Toggles
    public static ForgeConfigSpec.BooleanValue enableLootBallFinder;
    public static ForgeConfigSpec.BooleanValue enableLegiFinder;
    public static ForgeConfigSpec.BooleanValue enableUltraBeastFinder;
    public static ForgeConfigSpec.BooleanValue enableShinyFinder;
    public static ForgeConfigSpec.BooleanValue enableAutoFisher;
    public static ForgeConfigSpec.BooleanValue enableAutoBattle;
    public static ForgeConfigSpec.BooleanValue enableRaidFinder;
    public static ForgeConfigSpec.BooleanValue enableBossFinder;
    public static ForgeConfigSpec.BooleanValue enableBirdTempleFinder;
    public static ForgeConfigSpec.BooleanValue enableArcChaliceFinder;
    public static ForgeConfigSpec.BooleanValue enableFossilFinder;
    public static ForgeConfigSpec.BooleanValue enableWormholeFinder;

    // Finder Settings
    public static ForgeConfigSpec.IntValue lootBallScanRadius;
    public static ForgeConfigSpec.IntValue entityScanRadius;
    public static ForgeConfigSpec.IntValue updateInterval;

    // Display Settings
    public static ForgeConfigSpec.BooleanValue showStatusOverlay;
    public static ForgeConfigSpec.IntValue overlayPositionX;
    public static ForgeConfigSpec.IntValue overlayPositionY;
    public static ForgeConfigSpec.ConfigValue<String> overlayColor;

    // JourneyMap Integration
    public static ForgeConfigSpec.BooleanValue enableJourneyMapIntegration;
    public static ForgeConfigSpec.BooleanValue showWaypointLabels;
    public static ForgeConfigSpec.IntValue waypointDuration;

    static {
        BUILDER.push("General Settings");

        // Feature Toggles
        enableLootBallFinder = BUILDER
                .comment("Enable Loot Ball Finder feature")
                .define("enable_lootball_finder", true);
        DEFAULT_VALUES.put("enable_lootball_finder", true);

        enableLegiFinder = BUILDER
                .comment("Enable Legendary Pokemon Finder feature")
                .define("enable_legi_finder", true);
        DEFAULT_VALUES.put("enable_legi_finder", true);

        enableUltraBeastFinder = BUILDER
                .comment("Enable Ultra Beast Finder feature")
                .define("enable_ultrabeast_finder", true);
        DEFAULT_VALUES.put("enable_ultrabeast_finder", true);

        enableShinyFinder = BUILDER
                .comment("Enable Shiny Pokemon Finder feature")
                .define("enable_shiny_finder", true);
        DEFAULT_VALUES.put("enable_shiny_finder", true);

        enableAutoFisher = BUILDER
                .comment("Enable Auto Fisher feature")
                .define("enable_auto_fisher", false);
        DEFAULT_VALUES.put("enable_auto_fisher", false);

        enableAutoBattle = BUILDER
                .comment("Enable Auto Battle feature")
                .define("enable_auto_battle", false);
        DEFAULT_VALUES.put("enable_auto_battle", false);

        enableRaidFinder = BUILDER
                .comment("Enable Raid Finder feature")
                .define("enable_raid_finder", true);
        DEFAULT_VALUES.put("enable_raid_finder", true);

        enableBossFinder = BUILDER
                .comment("Enable Boss Pokemon Finder feature")
                .define("enable_boss_finder", true);
        DEFAULT_VALUES.put("enable_boss_finder", true);

        enableBirdTempleFinder = BUILDER
                .comment("Enable Bird Temple Finder feature")
                .define("enable_bird_temple_finder", true);
        DEFAULT_VALUES.put("enable_bird_temple_finder", true);

        enableArcChaliceFinder = BUILDER
                .comment("Enable Arc Chalice Finder feature")
                .define("enable_arc_chalice_finder", true);
        DEFAULT_VALUES.put("enable_arc_chalice_finder", true);

        enableFossilFinder = BUILDER
                .comment("Enable Fossil Finder feature")
                .define("enable_fossil_finder", true);
        DEFAULT_VALUES.put("enable_fossil_finder", true);

        enableWormholeFinder = BUILDER
                .comment("Enable Wormhole Finder feature")
                .define("enable_wormhole_finder", true);
        DEFAULT_VALUES.put("enable_wormhole_finder", true);

        BUILDER.pop();

        BUILDER.push("Scanner Settings");

        lootBallScanRadius = BUILDER
                .comment("Radius to scan for loot balls (in blocks)")
                .defineInRange("lootball_scan_radius", 64, 16, 128);
        DEFAULT_VALUES.put("lootball_scan_radius", 64);

        entityScanRadius = BUILDER
                .comment("Radius to scan for entities (in blocks)")
                .defineInRange("entity_scan_radius", 64, 16, 128);
        DEFAULT_VALUES.put("entity_scan_radius", 64);

        updateInterval = BUILDER
                .comment("Update interval for scanning (in ticks)")
                .defineInRange("update_interval", 20, 1, 100);
        DEFAULT_VALUES.put("update_interval", 20);

        BUILDER.pop();

        BUILDER.push("Display Settings");

        showStatusOverlay = BUILDER
                .comment("Show status overlay on screen")
                .define("show_status_overlay", true);
        DEFAULT_VALUES.put("show_status_overlay", true);

        overlayPositionX = BUILDER
                .comment("X position of the status overlay")
                .defineInRange("overlay_position_x", 2, 0, 4096);
        DEFAULT_VALUES.put("overlay_position_x", 2);

        overlayPositionY = BUILDER
                .comment("Y position of the status overlay")
                .defineInRange("overlay_position_y", 2, 0, 4096);
        DEFAULT_VALUES.put("overlay_position_y", 2);

        overlayColor = BUILDER
                .comment("Color of the overlay text (hex format)")
                .define("overlay_color", "FFFFFF");
        DEFAULT_VALUES.put("overlay_color", "FFFFFF");

        BUILDER.pop();

        BUILDER.push("JourneyMap Settings");

        enableJourneyMapIntegration = BUILDER
                .comment("Enable JourneyMap integration")
                .define("enable_journeymap_integration", true);
        DEFAULT_VALUES.put("enable_journeymap_integration", true);

        showWaypointLabels = BUILDER
                .comment("Show labels on waypoints")
                .define("show_waypoint_labels", true);
        DEFAULT_VALUES.put("show_waypoint_labels", true);

        waypointDuration = BUILDER
                .comment("Duration of temporary waypoints (in seconds, -1 for permanent)")
                .defineInRange("waypoint_duration", -1, -1, 3600);
        DEFAULT_VALUES.put("waypoint_duration", -1);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onConfigLoad(final ModConfig.Loading event) {
        try {
            LOGGER.info("Loading config: {}", event.getConfig().getFileName());
            syncConfigToNative();
        } catch (Exception e) {
            ErrorHandler.handleNativeError("Config Loading", e);
        }
    }

    @SubscribeEvent
    public static void onConfigReload(final ModConfig.Reloading event) {
        try {
            LOGGER.info("Reloading config: {}", event.getConfig().getFileName());
            syncConfigToNative();
        } catch (Exception e) {
            ErrorHandler.handleNativeError("Config Reloading", e);
        }
    }

    public static void syncConfigToNative() {
        if (!NativeBridge.isInitialized()) {
            LOGGER.warn("Native bridge not initialized, skipping config sync");
            return;
        }

        try {
            // Feature Toggles
            NativeBridge.updateSettings("enable_lootball_finder", enableLootBallFinder.get());
            NativeBridge.updateSettings("enable_legi_finder", enableLegiFinder.get());
            NativeBridge.updateSettings("enable_ultrabeast_finder", enableUltraBeastFinder.get());
            NativeBridge.updateSettings("enable_shiny_finder", enableShinyFinder.get());
            NativeBridge.updateSettings("enable_auto_fisher", enableAutoFisher.get());
            NativeBridge.updateSettings("enable_auto_battle", enableAutoBattle.get());
            NativeBridge.updateSettings("enable_raid_finder", enableRaidFinder.get());
            NativeBridge.updateSettings("enable_boss_finder", enableBossFinder.get());
            NativeBridge.updateSettings("enable_bird_temple_finder", enableBirdTempleFinder.get());
            NativeBridge.updateSettings("enable_arc_chalice_finder", enableArcChaliceFinder.get());
            NativeBridge.updateSettings("enable_fossil_finder", enableFossilFinder.get());
            NativeBridge.updateSettings("enable_wormhole_finder", enableWormholeFinder.get());

            // Scanner Settings
            NativeBridge.updateSettings("lootball_scan_radius", lootBallScanRadius.get());
            NativeBridge.updateSettings("entity_scan_radius", entityScanRadius.get());
            NativeBridge.updateSettings("update_interval", updateInterval.get());

            // Display Settings
            NativeBridge.updateSettings("show_status_overlay", showStatusOverlay.get());
            NativeBridge.updateSettings("overlay_position_x", overlayPositionX.get());
            NativeBridge.updateSettings("overlay_position_y", overlayPositionY.get());
            NativeBridge.updateSettings("overlay_color", overlayColor.get());

            // JourneyMap Settings
            NativeBridge.updateSettings("enable_journeymap_integration", enableJourneyMapIntegration.get());
            NativeBridge.updateSettings("show_waypoint_labels", showWaypointLabels.get());
            NativeBridge.updateSettings("waypoint_duration", waypointDuration.get());

            LOGGER.info("Config synchronized to native library successfully");
        } catch (Exception e) {
            ErrorHandler.handleNativeError("Config Sync", e);
        }
    }

    public static void resetToDefaults() {
        try {
            for (Map.Entry<String, Object> entry : DEFAULT_VALUES.entrySet()) {
                NativeBridge.updateSettings(entry.getKey(), entry.getValue());
            }
            LOGGER.info("Config reset to defaults");
        } catch (Exception e) {
            ErrorHandler.handleNativeError("Config Reset", e);
        }
    }
}
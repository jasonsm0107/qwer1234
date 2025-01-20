package net.sloth.config;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PUConfig {
    public static final PUConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;
    public final ForgeConfigSpec.ConfigValue<String> loadLootFilterOnStartup;
    public final ForgeConfigSpec.BooleanValue enableLootFilterOnStartup;
    public final ForgeConfigSpec.IntValue threadPoolSize;
    public final ForgeConfigSpec.IntValue autoFisherUpdateTickDelay;
    public final ForgeConfigSpec.IntValue updateDelay;
    public final ForgeConfigSpec.IntValue scanRadius;
    public final ForgeConfigSpec.ConfigValue<List<? extends String>> battleBlacklist;
    public final ForgeConfigSpec.BooleanValue autoAcknowledge;
    public final ForgeConfigSpec.BooleanValue pauseOnShinys;
    public final ForgeConfigSpec.BooleanValue pauseOnLegendaries;
    public final ForgeConfigSpec.BooleanValue pauseOnUltraBeasts;
    public final ForgeConfigSpec.BooleanValue pauseOnUncaught;

    private PUConfig(ForgeConfigSpec.Builder builder) {
        builder.push("pixelmonutils");
        builder.push("lootfilter");
        this.enableLootFilterOnStartup = builder.comment("Enable loot filter on startup").define("enable", false);
        this.loadLootFilterOnStartup = builder.comment(new String[]{"Loot filter file to load on startup", "Leave this field empty to disable startup loading"}).define("loadupFile", "");
        builder.pop();
        builder.push("battle");
        this.autoAcknowledge = builder.comment("Automatically acknowledge battle messages an level ups").define("acknowledge", false);
        this.pauseOnShinys = builder.comment("Prevents auto battle to attack shiny pokemon").define("blacklistShiny", false);
        this.pauseOnLegendaries = builder.comment("Prevents auto battle to attack legendary pokemon").define("blacklistLegendary", false);
        this.pauseOnUltraBeasts = builder.comment("Prevents auto battle to attack ultra beasts pokemon").define("blacklistUltraBeasts", false);
        this.pauseOnUncaught = builder.comment("Prevents auto battle to attack uncaught pokemon").define("blacklistUncaught", false);
        this.battleBlacklist = builder.comment("List of Pokemon added to the battle blacklist using the #battle blacklist add command").defineList("blacklist", CopyOnWriteArrayList::new, (unknown) -> {
            try {
                int dex = Integer.parseInt(unknown.toString());
                return dex > 0;
            } catch (Exception var2) {
                return false;
            }
        });
        builder.pop();
        builder.push("autofisher");
        this.autoFisherUpdateTickDelay = builder.comment(new String[]{"Amount of ticks to skip before updating the auto fisher", "A too low value might causes weird rod spamming"}).defineInRange("tickDelay", 10, 1, 500);
        builder.pop();
        builder.push("processing");
        this.threadPoolSize = builder.comment(new String[]{"Amount of offside threads used to execute the finder and helper tasks", "Requires restart"}).defineInRange("poolSize", 5, 1, 20);
        this.updateDelay = builder.comment(new String[]{"Seconds between Waypoint Updates", "Requires restart"}).defineInRange("updateDelay", 4, 1, 600);
        this.scanRadius = builder.comment("Radius in block to be scanned").defineInRange("scanRadius", 64, 2, 512);
        builder.pop();
    }

    static {
        Pair<PUConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(PUConfig::new);
        INSTANCE = (PUConfig)pair.getKey();
        SPEC = (ForgeConfigSpec)pair.getRight();
    }
}

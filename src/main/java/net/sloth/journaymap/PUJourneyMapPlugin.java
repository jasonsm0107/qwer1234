package net.sloth.journaymap;


import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.battles.raids.RaidData;
import com.pixelmonmod.pixelmon.client.storage.ClientStorageManager;
import com.pixelmonmod.pixelmon.entities.DenEntity;
import com.pixelmonmod.pixelmon.entities.WormholeEntity;
import com.pixelmonmod.pixelmon.entities.npcs.NPCEntity;
import com.pixelmonmod.pixelmon.entities.pixelmon.AbstractBaseEntity;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
//import net.sloth.command.CatchSeriesHelperCommand;
//import net.sloth.command.PokemonLocatorCommand;
import net.sloth.feature.BossFinderMode;
import net.sloth.feature.LegiFinderMode;
import net.sloth.feature.LootBallFinderMode;
import net.sloth.feature.PokedexHelperMode;
import net.sloth.feature.RaidFinderMode;
import net.sloth.feature.ShinyFinderMode;
import net.sloth.feature.UltraBeastFinderMode;
import net.sloth.storage.StorageManager;
import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;
import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.event.ClientEvent;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ClientPlugin
public class PUJourneyMapPlugin implements IClientPlugin {
    private static final Logger log = LogManager.getLogger("pixelmonutils/JourneyMap");
    public static final CopyOnWriteArrayList<BlockMarker> BLOCK_MARKERS;
    public static final EntityMarker<PixelmonEntity> POKEDEX_HELPER;
    public static final EntityMarker<PixelmonEntity> FIND_COMMAND_FORMS;
    public static final EntityMarker<PixelmonEntity> FIND_COMMAND_PALETTE;
    public static final EntityMarker<PixelmonEntity> LEGENDARY_FINDER;
    public static final EntityMarker<PixelmonEntity> ULTRA_BEASTS_FINDER;
    public static final CopyOnWriteArrayList<EntityMarker<?>> ENTITY_MARKERS;
    @Nullable
    public static IClientAPI API;

    public PUJourneyMapPlugin() {
    }

    public void initialize(IClientAPI api) {
        log.info("Registering JourneyMap Plugin...");
        API = api;
        log.info("JourneyMap Plugin registered");
    }

    public String getModId() {
        return "ingka1111";
    }

    public void onEvent(ClientEvent clientEvent) {
    }

    static {
        BLOCK_MARKERS = new CopyOnWriteArrayList(Lists.newArrayList(new BlockMarker[]{BlockMarker.with(PUSettings::getBirdTempleFinderMode), BlockMarker.with((world, pos, state) -> {
            if (PUSettings.getLootBallFinderMode().getValidBlockTest().test(state)) {
                return !StorageManager.getStorage().knowsLootBall(world.dimension().location(), pos);
            } else {
                return false;
            }
        }, () -> {
            return !PUSettings.getLootBallFinderMode().equals(LootBallFinderMode.OFF);
        }, PUFeatureColors.LOOT_BALL), BlockMarker.with(PUSettings::getIlexShrineFinderMode), BlockMarker.with(PUSettings::getZygardeLocationMode), BlockMarker.with(PUSettings::getArcChaliceFinderMode), BlockMarker.with(PUSettings::getChestFinderMode), BlockMarker.with(PUSettings::getFossilFinderMode)}));
        POKEDEX_HELPER = EntityMarker.withPixelmon((world, pos, entity) -> {
            return entity.getOwner() == null && !ClientStorageManager.pokedex.hasCaught(entity.getSpecies());
        }, () -> {
            return PUSettings.getPokedexHelperMode().equals(PokedexHelperMode.ON);
        }, PUFeatureColors.POKEDEX_HELPER);
        FIND_COMMAND_FORMS = EntityMarker.withPixelmon((world, pos, entity) -> {
            if (entity.getOwner() != null) {
                return false;
            } else {

                return false;
            }
        }, () -> {
            return false;
        }, PUFeatureColors.FIND_COMMAND);
        FIND_COMMAND_PALETTE = EntityMarker.withPixelmon((world, pos, entity) -> {
            if (entity.getOwner() != null) {
                return false;
            } /*else if (PokemonLocatorCommand.getSearchedPokemonPalettes().containsKey(entity.getSpecies())) {
                Collection<String> palettes = PokemonLocatorCommand.getSearchedPokemonPalettes().get(entity.getSpecies());
                String palette = entity.getPalette().getLocalizedName();
                return palettes.contains(palette);
            } */else {
                return false;
            }
        }, () -> {
            return false;
        }, PUFeatureColors.FIND_COMMAND);
        LEGENDARY_FINDER = EntityMarker.withPixelmon((world, pos, entity) -> {
            return entity.getOwner() == null && entity.isLegendary();
        }, () -> {
            return PUSettings.getLegiFinderMode().equals(LegiFinderMode.ON);
        }, PUFeatureColors.LEGENDARY_POKEMON);
        ULTRA_BEASTS_FINDER = EntityMarker.withPixelmon((world, pos, entity) -> {
            return entity.getSpecies().isUltraBeast() && entity.func_184780_dh() == null;
        }, () -> {
            return PUSettings.getUltraBeastFinderMode().equals(UltraBeastFinderMode.ON);
        }, PUFeatureColors.ULTRA_BEAST);
        ENTITY_MARKERS = new CopyOnWriteArrayList(Lists.newArrayList(new EntityMarker[]{EntityMarker.with(AbstractBaseEntity.class, (world, pos, entity) -> {
            return entity.isBossPokemon();
        }, () -> {
            return PUSettings.getBossFinderMode().equals(BossFinderMode.ALL);
        }, (entity) -> {
            return String.format("%s %s boss", entity.getBossTier().getID(), entity.getLocalizedName());
        }, PUFeatureColors.BOSS_POKEMON), EntityMarker.withPixelmon((world, pos, entity) -> {

                return false;
        }, () -> {
            return false;
        }, PUFeatureColors.CATCH_SERIES), FIND_COMMAND_FORMS, FIND_COMMAND_PALETTE, LEGENDARY_FINDER, POKEDEX_HELPER, EntityMarker.with(DenEntity.class, (world, pos, entity) -> {
            if (!entity.getData().isPresent()) {
                return false;
            } else {
                RaidData raidData = (RaidData)entity.getData().get();
                int stars = raidData.getStars();
                return PUSettings.getRaidFinderMode().getMinStarLevel() <= stars;
            }
        }, () -> {
            return !PUSettings.getRaidFinderMode().equals(RaidFinderMode.OFF);
        }, (entity) -> {
            StringBuilder waypointName = new StringBuilder(((RaidData)entity.getData().get()).getSpecies().getName() + " ");

            for(int i = 0; i < ((RaidData)entity.getData().get()).getStars(); ++i) {
                waypointName.append("⭐");
            }

            return waypointName.toString();
        }, PUFeatureColors.RAID_FINDER), EntityMarker.withPixelmon((world, pos, entity) -> {
            return entity.getOwner() == null && !entity.isBossPokemon() && entity.getPokemon().isShiny();
        }, () -> {
            return PUSettings.getShinyFinderMode().equals(ShinyFinderMode.ON);
        }, PUFeatureColors.SHINY_POKEMON), ULTRA_BEASTS_FINDER, EntityMarker.with(NPCEntity.class, (world, pos, entity) -> {
            return PUSettings.getNpcFinderMode().isValid(entity);
        }, () -> {
            return PUSettings.getNpcFinderMode().isEnabled();
        }, (npcEntity) -> {
            String name = npcEntity.getDisplayText();
            if (name.isEmpty()) {
                name = npcEntity.func_200200_C_().getString();
                if (name.isEmpty()) {
                    name = "NPC without Name";
                }
            }

            return name;
        }, PUFeatureColors.NPC_FINDER), EntityMarker.with(ChestMinecartEntity.class, (world, pos, entity) -> {
            return true;
        }, () -> {
            return PUSettings.getChestFinderMode().isEnabled();
        }, (entity) -> {
            return Items.CHEST_MINECART.getDefaultInstance().getHoverName().getString();
        }, PUFeatureColors.CHEST_COMMAND), EntityMarker.with(WormholeEntity.class, (world, pos, entity) -> {
            return true;
        }, () -> {
            return PUSettings.getWormholeFinderMode().isEnabled();
        }, (wormholeEntity) -> {
            return wormholeEntity.getName().getString();
        }, PUFeatureColors.WORMHOLE)}));
        API = null;
    }
}

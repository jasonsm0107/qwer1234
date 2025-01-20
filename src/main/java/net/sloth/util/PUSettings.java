package net.sloth.util;

import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import net.minecraft.util.text.TranslationTextComponent;
//import net.sloth.command.CatchSeriesHelperCommand;
//import net.sloth.command.ResetPokemonCommand;
//import net.sloth.command.TimedCommandsCommand;
import net.sloth.feature.*;

public class PUSettings {
    private static final Object $LOCK = new Object[0];
    private static ZygardeLocationMode zygardeLocationMode;
    private static LegiFinderMode legiFinderMode;
    private static WormholeFinderMode wormholeFinderMode;
    private static PokedexHelperMode pokedexHelperMode;
    private static LootBallFinderMode lootBallFinderMode;
    private static AutoFisherMode autoFisherMode;
    private static ShinyFinderMode shinyFinderMode;
    private static AutoBattleMode autoBattleMode;
    private static RaidFinderMode raidFinderMode;
    private static BossFinderMode bossFinderMode;
    private static UltraBeastFinderMode ultraBeastFinderMode;
    private static BirdTempleFinderMode birdTempleFinderMode;
    private static IlexShrineFinderMode ilexShrineFinderMode;
    private static ArcChaliceFinderMode arcChaliceFinderMode;
    private static VanillaChestFinderMode chestFinderMode;
    private static FossilFinderMode fossilFinderMode;
    private static NPCFinderMode npcFinderMode;
    private static boolean featureEnabled = false;

    public PUSettings() {
    }

    public static boolean isFeatureEnabled() {
        return featureEnabled;
    }

    public static void setFeatureEnabled(boolean enabled) {
        featureEnabled = enabled;
    }

    

    public static void reset() {
        setZygardeLocationMode(ZygardeLocationMode.OFF);
        setLegiFinderMode(LegiFinderMode.OFF);
        setPokedexHelperMode(PokedexHelperMode.OFF);
        setLootBallFinderMode(LootBallFinderMode.OFF);
        setAutoFisherMode(AutoFisherMode.OFF);
        setShinyFinderMode(ShinyFinderMode.OFF);
        setAutoBattleMode(AutoBattleMode.OFF);
        setBossFinderMode(BossFinderMode.OFF);
        setUltraBeastFinderMode(UltraBeastFinderMode.OFF);
        setBirdTempleFinderMode(BirdTempleFinderMode.OFF);
        setIlexShrineFinderMode(IlexShrineFinderMode.OFF);
        setArcChaliceFinderMode(ArcChaliceFinderMode.OFF);
        setChestFinderMode(VanillaChestFinderMode.OFF);
        setNpcFinderMode(NPCFinderMode.OFF);
        setFossilFinderMode(FossilFinderMode.OFF);
        setWormholeFinderMode(WormholeFinderMode.OFF);
        //ResetPokemonCommand.reset();
        //TimedCommandsCommand.reset();
        //CatchSeriesHelperCommand.setTarget((Species)null);
    }

    public static String getModeText(IFeatureStatus mode) {
        if (mode != null) {
            TranslationTextComponent text = mode.getStatusText();
            if (text != null) {
                return text.getString();
            }
        }
        return "OFF";
    }

    public static ZygardeLocationMode getZygardeLocationMode() {
        synchronized($LOCK) {
            return zygardeLocationMode;
        }
    }

    public static void setZygardeLocationMode(ZygardeLocationMode zygardeLocationMode) {
        synchronized($LOCK) {
            PUSettings.zygardeLocationMode = zygardeLocationMode;
        }
    }

    public static LegiFinderMode getLegiFinderMode() {
        synchronized($LOCK) {
            return legiFinderMode;
        }
    }

    public static void setLegiFinderMode(LegiFinderMode legiFinderMode) {
        synchronized($LOCK) {
            PUSettings.legiFinderMode = legiFinderMode;
        }
    }

    public static WormholeFinderMode getWormholeFinderMode() {
        synchronized($LOCK) {
            return wormholeFinderMode;
        }
    }

    public static void setWormholeFinderMode(WormholeFinderMode wormholeFinderMode) {
        synchronized($LOCK) {
            PUSettings.wormholeFinderMode = wormholeFinderMode;
        }
    }

    public static PokedexHelperMode getPokedexHelperMode() {
        synchronized($LOCK) {
            return pokedexHelperMode;
        }
    }

    public static void setPokedexHelperMode(PokedexHelperMode pokedexHelperMode) {
        synchronized($LOCK) {
            PUSettings.pokedexHelperMode = pokedexHelperMode;
        }
    }

    public static LootBallFinderMode getLootBallFinderMode() {
        synchronized($LOCK) {
            return lootBallFinderMode;
        }
    }

    public static void setLootBallFinderMode(LootBallFinderMode lootBallFinderMode) {
        synchronized($LOCK) {
            PUSettings.lootBallFinderMode = lootBallFinderMode;
        }
    }

    public static AutoFisherMode getAutoFisherMode() {
        synchronized($LOCK) {
            return autoFisherMode;
        }
    }

    public static void setAutoFisherMode(AutoFisherMode autoFisherMode) {
        synchronized($LOCK) {
            PUSettings.autoFisherMode = autoFisherMode;
        }
    }

    public static ShinyFinderMode getShinyFinderMode() {
        synchronized($LOCK) {
            return shinyFinderMode;
        }
    }

    public static void setShinyFinderMode(ShinyFinderMode shinyFinderMode) {
        synchronized($LOCK) {
            PUSettings.shinyFinderMode = shinyFinderMode;
        }
    }

    public static AutoBattleMode getAutoBattleMode() {
        synchronized($LOCK) {
            return autoBattleMode;
        }
    }

    public static void setAutoBattleMode(AutoBattleMode autoBattleMode) {
        synchronized($LOCK) {
            PUSettings.autoBattleMode = autoBattleMode;
        }
    }

    public static RaidFinderMode getRaidFinderMode() {
        synchronized($LOCK) {
            return raidFinderMode;
        }
    }

    public static void setRaidFinderMode(RaidFinderMode raidFinderMode) {
        synchronized($LOCK) {
            PUSettings.raidFinderMode = raidFinderMode;
        }
    }

    public static BossFinderMode getBossFinderMode() {
        synchronized($LOCK) {
            return bossFinderMode;
        }
    }

    public static void setBossFinderMode(BossFinderMode bossFinderMode) {
        synchronized($LOCK) {
            PUSettings.bossFinderMode = bossFinderMode;
        }
    }

    public static UltraBeastFinderMode getUltraBeastFinderMode() {
        synchronized($LOCK) {
            return ultraBeastFinderMode;
        }
    }

    public static void setUltraBeastFinderMode(UltraBeastFinderMode ultraBeastFinderMode) {
        synchronized($LOCK) {
            PUSettings.ultraBeastFinderMode = ultraBeastFinderMode;
        }
    }

    public static BirdTempleFinderMode getBirdTempleFinderMode() {
        synchronized($LOCK) {
            return birdTempleFinderMode;
        }
    }

    public static void setBirdTempleFinderMode(BirdTempleFinderMode birdTempleFinderMode) {
        synchronized($LOCK) {
            PUSettings.birdTempleFinderMode = birdTempleFinderMode;
        }
    }

    public static IlexShrineFinderMode getIlexShrineFinderMode() {
        synchronized($LOCK) {
            return ilexShrineFinderMode;
        }
    }

    public static void setIlexShrineFinderMode(IlexShrineFinderMode ilexShrineFinderMode) {
        synchronized($LOCK) {
            PUSettings.ilexShrineFinderMode = ilexShrineFinderMode;
        }
    }

    public static ArcChaliceFinderMode getArcChaliceFinderMode() {
        synchronized($LOCK) {
            return arcChaliceFinderMode;
        }
    }

    public static void setArcChaliceFinderMode(ArcChaliceFinderMode arcChaliceFinderMode) {
        synchronized($LOCK) {
            PUSettings.arcChaliceFinderMode = arcChaliceFinderMode;
        }
    }

    public static VanillaChestFinderMode getChestFinderMode() {
        synchronized($LOCK) {
            return chestFinderMode;
        }
    }

    public static void setChestFinderMode(VanillaChestFinderMode chestFinderMode) {
        synchronized($LOCK) {
            PUSettings.chestFinderMode = chestFinderMode;
        }
    }

    public static FossilFinderMode getFossilFinderMode() {
        synchronized($LOCK) {
            return fossilFinderMode;
        }
    }

    public static void setFossilFinderMode(FossilFinderMode fossilFinderMode) {
        synchronized($LOCK) {
            PUSettings.fossilFinderMode = fossilFinderMode;
        }
    }

    public static NPCFinderMode getNpcFinderMode() {
        synchronized($LOCK) {
            return npcFinderMode;
        }
    }

    public static void setNpcFinderMode(NPCFinderMode npcFinderMode) {
        synchronized($LOCK) {
            PUSettings.npcFinderMode = npcFinderMode;
        }
    }

    static {
        zygardeLocationMode = ZygardeLocationMode.OFF;
        legiFinderMode = LegiFinderMode.OFF;
        wormholeFinderMode = WormholeFinderMode.OFF;
        pokedexHelperMode = PokedexHelperMode.OFF;
        lootBallFinderMode = LootBallFinderMode.OFF;
        autoFisherMode = AutoFisherMode.OFF;
        shinyFinderMode = ShinyFinderMode.OFF;
        autoBattleMode = AutoBattleMode.OFF;
        raidFinderMode = RaidFinderMode.OFF;
        bossFinderMode = BossFinderMode.OFF;
        ultraBeastFinderMode = UltraBeastFinderMode.OFF;
        birdTempleFinderMode = BirdTempleFinderMode.OFF;
        ilexShrineFinderMode = IlexShrineFinderMode.OFF;
        arcChaliceFinderMode = ArcChaliceFinderMode.OFF;
        chestFinderMode = VanillaChestFinderMode.OFF;
        fossilFinderMode = FossilFinderMode.OFF;
        npcFinderMode = NPCFinderMode.OFF;
    }
}

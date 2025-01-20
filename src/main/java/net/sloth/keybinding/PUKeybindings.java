package net.sloth.keybinding;

import net.minecraft.client.Minecraft;
import net.sloth.GUI.GUImain;
import net.sloth.feature.ArcChaliceFinderMode;
import net.sloth.feature.UltraBeastFinderMode;
import net.sloth.feature.ZygardeLocationMode;
import net.sloth.util.PUSettings;
import java.util.ArrayList;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class PUKeybindings {
    private static final ArrayList<PUKeybinding> keybindings = new ArrayList();
    private static final KeybindingCategory GENERAL_CATEGORY = KeybindingCategory.of("general");
    // public static final PUKeybinding ZYGARDE_CHANGE_MODE;
    public static final PUKeybinding LEGENDARY_CHANGE_MODE;
    public static final PUKeybinding ULTRA_BEAST_CHANGE_MODE;
    // public static final PUKeybinding FISHER_CHANGE_MODE;
    // public static final PUKeybinding POKEDEX_HELPER_CHANGE_MODE;
    public static final PUKeybinding LOOT_BALL_MODE;
    public static final PUKeybinding SHINY_FINDER_MODE;
    public static final PUKeybinding RAID_FINDER_MODE;
    // public static final PUKeybinding AUTO_BATTLE_MODE;
    public static final PUKeybinding BOSS_FINDER_MODE;
    public static final PUKeybinding BIRD_SHRINE_FINDER_MODE;
    // public static final PUKeybinding ILEX_SHRINE_FINDER_MODE;
    public static final PUKeybinding ARC_CHALICE_FINDER_MODE;
    // public static final PUKeybinding CHEST_FINDER_MODE;
    // public static final PUKeybinding NPC_FINDER_MODE;
    public static final PUKeybinding FOSSIL_FINDER_MODE;
    public static final PUKeybinding WORMHOLE_FINDER_MODE;
    public static final PUKeybinding OPEN_GUI_MODE;
    public static final PUKeybinding TOGGLE_FEATURE_KEY;
    public static final UltraBeastFinderMode ULTRA_BEAST_FINDER_MODE = UltraBeastFinderMode.OFF;

    public PUKeybindings() {
    }

    public static void init() {
        if (keybindings.isEmpty()) {
            throw new IllegalStateException("Tried to register Keybindings but they already have been registered!");
        } else {
            keybindings.forEach(ClientRegistry::registerKeyBinding);
        }
    }

    private static PUKeybinding register(PUKeybinding keybinding) {
        keybindings.add(keybinding);
        return keybinding;
    }

    public static void checkKeyBindings() {
        keybindings.forEach((keyBinding) -> {
            if (keyBinding.isDown()) {
                keyBinding.getAction().onPress();
            }

        });
    }

    static {
        // GUI 열기 키
        OPEN_GUI_MODE = register(new PUKeybinding("opengui.mode.open", GENERAL_CATEGORY, () -> {
            Minecraft.getInstance().setScreen(new net.sloth.GUI.GUImain());
        }));

        // 기능 활성화/비활성화 키
        TOGGLE_FEATURE_KEY = register(new PUKeybinding("feature.toggle", GENERAL_CATEGORY, () -> {
            if (PUSettings.isFeatureEnabled()) {
                ULTRA_BEAST_FINDER_MODE.next();

            } else {
                ULTRA_BEAST_FINDER_MODE.next();
            }
        }));
    }

    static {
        /*ZYGARDE_CHANGE_MODE = register(new PUKeybinding("zygarde.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getZygardeLocationMode().next();
        }));*/

        LEGENDARY_CHANGE_MODE = register(new PUKeybinding("legifinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getLegiFinderMode().next();
        }));
        ULTRA_BEAST_CHANGE_MODE = register(new PUKeybinding("ultrabeastfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getUltraBeastFinderMode().next();
        }));
        /*FISHER_CHANGE_MODE = register(new PUKeybinding("autofisher.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getAutoFisherMode().next();
        }));
        POKEDEX_HELPER_CHANGE_MODE = register(new PUKeybinding("pokedexhelper.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getPokedexHelperMode().next();
        })); */
        LOOT_BALL_MODE = register(new PUKeybinding("lootballfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getLootBallFinderMode().next();
        }));
        SHINY_FINDER_MODE = register(new PUKeybinding("shinyfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getShinyFinderMode().next();
        }));
        RAID_FINDER_MODE = register(new PUKeybinding("raidfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getRaidFinderMode().next();
        }));
        /*AUTO_BATTLE_MODE = register(new PUKeybinding("autobattle.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getAutoBattleMode().next();
        }));*/
        BOSS_FINDER_MODE = register(new PUKeybinding("bossfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getBossFinderMode().next();
        }));
        BIRD_SHRINE_FINDER_MODE = register(new PUKeybinding("birdshrinefinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getBirdTempleFinderMode().next();
        }));
        /*ILEX_SHRINE_FINDER_MODE = register(new PUKeybinding("ilexshrinefinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getIlexShrineFinderMode().next();
        }));*/
        ARC_CHALICE_FINDER_MODE = register(new PUKeybinding("arcchalicefinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getArcChaliceFinderMode().next();
        }));
        /*CHEST_FINDER_MODE = register(new PUKeybinding("chestfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getChestFinderMode().next();
        }));
        NPC_FINDER_MODE = register(new PUKeybinding("npcfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getNpcFinderMode().next();
        }));*/
        FOSSIL_FINDER_MODE = register(new PUKeybinding("fossilfinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getFossilFinderMode().next();
        }));
        WORMHOLE_FINDER_MODE = register(new PUKeybinding("wormholefinder.mode.change", GENERAL_CATEGORY, () -> {
            PUSettings.getWormholeFinderMode().next();
        }));

    }
}

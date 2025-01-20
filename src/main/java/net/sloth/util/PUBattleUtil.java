package net.sloth.util;

import com.pixelmonmod.pixelmon.client.gui.ItemDropsScreen;
import com.pixelmonmod.pixelmon.client.gui.battles.BattleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public class PUBattleUtil {
    public PUBattleUtil() {
    }

    public static boolean isOutOfBattle() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof BattleScreen) {
            return false;
        } else {
            return !(screen instanceof ItemDropsScreen);
        }
    }
}

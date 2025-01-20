package net.sloth.util;

import com.pixelmonmod.pixelmon.client.gui.battles.BattleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class PUSoundUtil {
    public PUSoundUtil() {
    }

    public static void playAmbientIfOutOfBattle(SoundEvent sound) {
        if (sound != null) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && !(mc.screen instanceof BattleScreen)) {
                mc.player.playSound(sound, mc.options.getSoundSourceVolume(SoundCategory.AMBIENT), 1.0F);
            }

        }
    }
}

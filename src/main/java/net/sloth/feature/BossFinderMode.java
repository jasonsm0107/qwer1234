package net.sloth.feature;

import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum BossFinderMode implements IFeatureStatus {
    ALL("all", 0, true),
    OFF("", 999, false);

    private final String statusText;
    private final int minStarLevel;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("bossfinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.BOSS_POKEMON;
    }

    public void next() {
        switch (this) {
            case ALL:
                PUSettings.setBossFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setBossFinderMode(ALL);
        }

    }

    private BossFinderMode(String statusText, int minStarLevel, boolean enabled) {
        this.statusText = statusText;
        this.minStarLevel = minStarLevel;
        this.enabled = enabled;
    }

    public int getMinStarLevel() {
        return this.minStarLevel;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

package net.sloth.feature;

import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum RaidFinderMode implements IFeatureStatus {
    ALL("all", 0, true),
    STAR_1("star.1", 1, true),
    STAR_2("star.2", 2, true),
    STAR_3("star.3", 3, true),
    STAR_4("star.4", 4, true),
    STAR_5("star.5", 5, true),
    OFF("", 999, false);

    private final String statusText;
    private final int minStarLevel;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("raidfinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.RAID_FINDER;
    }

    public void next() {
        switch (this) {
            case ALL:
                PUSettings.setRaidFinderMode(STAR_1);
                break;
            case STAR_1:
                PUSettings.setRaidFinderMode(STAR_2);
                break;
            case STAR_2:
                PUSettings.setRaidFinderMode(STAR_3);
                break;
            case STAR_3:
                PUSettings.setRaidFinderMode(STAR_4);
                break;
            case STAR_4:
                PUSettings.setRaidFinderMode(STAR_5);
                break;
            case STAR_5:
                PUSettings.setRaidFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setRaidFinderMode(ALL);
        }

    }

    private RaidFinderMode(String statusText, int minStarLevel, boolean enabled) {
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

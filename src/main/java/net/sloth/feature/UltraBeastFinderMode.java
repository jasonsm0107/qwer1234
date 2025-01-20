package net.sloth.feature;

import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum UltraBeastFinderMode implements IFeatureStatus {
    ON("on", true),
    OFF("", false);

    private final String statusText;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("ultrabeastfinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.ULTRA_BEAST;
    }

    public void next() {
        switch (this) {
            case ON:
                PUSettings.setUltraBeastFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setUltraBeastFinderMode(ON);
        }

    }

    private UltraBeastFinderMode(String statusText, boolean enabled) {
        this.statusText = statusText;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

package net.sloth.feature;

import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum WormholeFinderMode implements IFeatureStatus {
    ON("on", true),
    OFF("", false);

    private final String statusText;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("wormholefinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.WORMHOLE;
    }

    public void next() {
        switch (this) {
            case ON:
                PUSettings.setWormholeFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setWormholeFinderMode(ON);
        }

    }

    private WormholeFinderMode(String statusText, boolean enabled) {
        this.statusText = statusText;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

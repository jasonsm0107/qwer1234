package net.sloth.feature;

import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum AutoFisherMode implements IFeatureStatus {
    ON("on", true),
    OFF("", false);

    private final String statusText;
    private final boolean enabled;
    private static final Color color = new Color(0, 128, 255);

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("autofisher.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return color;
    }

    public void next() {
        switch (this) {
            case ON:
                PUSettings.setAutoFisherMode(OFF);
                break;
            case OFF:
                PUSettings.setAutoFisherMode(ON);
        }

    }

    private AutoFisherMode(String statusText, boolean enabled) {
        this.statusText = statusText;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

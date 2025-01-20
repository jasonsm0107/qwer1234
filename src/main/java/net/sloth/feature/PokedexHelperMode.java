package net.sloth.feature;

import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum PokedexHelperMode implements IFeatureStatus {
    ON("on", false),
    OFF("", true);

    private final String statusText;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("pokedexhelper.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.POKEDEX_HELPER;
    }

    public void next() {
        switch (this) {
            case ON:
                PUSettings.setPokedexHelperMode(OFF);
                break;
            case OFF:
                PUSettings.setPokedexHelperMode(ON);
        }

    }

    private PokedexHelperMode(String statusText, boolean enabled) {
        this.statusText = statusText;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
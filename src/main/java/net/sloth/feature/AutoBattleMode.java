package net.sloth.feature;

import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum AutoBattleMode implements IFeatureStatus {
    ON("on", true),
    OFF("", false);

    private final String statusText;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("autobattle.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.AUTO_BATTLE;
    }

    public void next() {
        switch (this) {
            case ON:
                PUSettings.setAutoBattleMode(OFF);
                break;
            case OFF:
                PUSettings.setAutoBattleMode(ON);
        }

    }

    private AutoBattleMode(String statusText, boolean enabled) {
        this.statusText = statusText;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

package net.sloth.feature;

import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public interface IFeatureStatus {
    TranslationTextComponent getStatusText();

    Color getColor();

    boolean isEnabled();
}


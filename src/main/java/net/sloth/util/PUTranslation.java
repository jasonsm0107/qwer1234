package net.sloth.util;

import net.minecraft.util.text.TranslationTextComponent;

public class PUTranslation {
    public PUTranslation() {
    }

    public static TranslationTextComponent of(String path, Object... args) {
        return new TranslationTextComponent(String.format("%s.%s", "pixelmonutils", path), args);
    }
}

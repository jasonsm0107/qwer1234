package net.sloth.feature;

import com.pixelmonmod.pixelmon.api.registries.PixelmonBlocks;
import net.minecraft.block.BlockState;
import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import net.minecraft.util.text.TranslationTextComponent;

public enum ArcChaliceFinderMode implements IBlockTarget {
    ON("on", (blockState) -> {
        return blockState.equals(PixelmonBlocks.arc_chalice);
    }, true),
    OFF("", (blockState) -> {
        return false;
    }, false);

    private final String statusText;
    private final Predicate<BlockState> validBlockTest;
    private final boolean enabled;

    @Nullable
    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("arcchalicefinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.ARC_CHALICE_COMMAND;
    }

    public void next() {
        switch (this) {
            case ON:
                PUSettings.setArcChaliceFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setArcChaliceFinderMode(ON);
        }

    }

    private ArcChaliceFinderMode(String statusText, Predicate validBlockTest, boolean enabled) {
        this.statusText = statusText;
        this.validBlockTest = validBlockTest;
        this.enabled = enabled;
    }

    public Predicate<BlockState> getValidBlockTest() {
        return this.validBlockTest;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

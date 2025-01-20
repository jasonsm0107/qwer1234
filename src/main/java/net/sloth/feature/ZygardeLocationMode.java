package net.sloth.feature;

import com.pixelmonmod.pixelmon.api.registries.PixelmonBlocks;
import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.text.TranslationTextComponent;

public enum ZygardeLocationMode implements IBlockTarget {
    BOTH("both", (blockState) -> {
        return blockState.equals(PixelmonBlocks.zygarde_core) || blockState.equals(PixelmonBlocks.zygarde_cell);
    }, true),
    CORE("cores", (blockState) -> {
        return blockState.equals(PixelmonBlocks.zygarde_core);
    }, true),
    CELL("cells", (blockState) -> {
        return blockState.equals(PixelmonBlocks.zygarde_cell);
    }, true),
    OFF("", (blockState) -> {
        return false;
    }, false);

    private final String statusText;
    private final Predicate<BlockState> validBlockTest;
    private final boolean enabled;

    @Nullable
    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("zygardefinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.ZYGARDE_FINDER;
    }

    public void next() {
        switch (this) {
            case BOTH:
                PUSettings.setZygardeLocationMode(CORE);
                break;
            case CORE:
                PUSettings.setZygardeLocationMode(CELL);
                break;
            case CELL:
                PUSettings.setZygardeLocationMode(OFF);
                break;
            case OFF:
                PUSettings.setZygardeLocationMode(BOTH);
        }

    }

    private ZygardeLocationMode(String statusText, Predicate validBlockTest, boolean enabled) {
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

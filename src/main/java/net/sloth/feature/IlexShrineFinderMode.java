package net.sloth.feature;

import com.pixelmonmod.pixelmon.api.registries.PixelmonBlocks;
import com.pixelmonmod.pixelmon.blocks.enums.EnumBlockPos;
import com.pixelmonmod.pixelmon.blocks.machines.IlexShrineBlock;
import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.text.TranslationTextComponent;

public enum IlexShrineFinderMode implements IBlockTarget {
    /*ON("on", (blockState) -> {
        return blockState.equals(PixelmonBlocks.ilex_shrine) && ((EnumBlockPos)blockState.func_177229_b(IlexShrineBlock.BLOCKPOS)).equals(EnumBlockPos.BOTTOM);
    }, true),*/
    OFF("", (blockState) -> {
        return false;
    }, false);

    private final String statusText;
    private final Predicate<BlockState> validBlockTest;
    private final boolean enabled;

    @Nullable
    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("ilexshrinefinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.ILEX_SHRINE;
    }

    public void next() {
        /*switch (this) {
            case ON:
                PUSettings.setIlexShrineFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setIlexShrineFinderMode(ON);
        }*/

    }

    private IlexShrineFinderMode(String statusText, Predicate validBlockTest, boolean enabled) {
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
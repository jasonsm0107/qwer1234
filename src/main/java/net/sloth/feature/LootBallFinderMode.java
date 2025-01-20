package net.sloth.feature;

import com.pixelmonmod.pixelmon.api.registries.PixelmonBlocks;
import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.util.text.TranslationTextComponent;

public enum LootBallFinderMode implements IFeatureStatus {
    ALL("all", (blockState) -> {
        return blockState.equals(PixelmonBlocks.master_chest) || blockState.equals(PixelmonBlocks.beast_chest) || blockState.equals(PixelmonBlocks.ultra_chest) || blockState.equals(PixelmonBlocks.poke_chest) || blockState.equals(PixelmonBlocks.poke_gift);
    }, true),
    MATER_ONLY("master", (blockState) -> {
        return blockState.equals(PixelmonBlocks.master_chest);
    }, true),
    BEAST_ONLY("beast", (blockState) -> {
        return blockState.equals(PixelmonBlocks.beast_chest);
    }, true),
    ULTRA_ONLY("ultra", (blockState) -> {
        return blockState.equals(PixelmonBlocks.ultra_chest);
    }, true),
    POKE_ONLY("poke", (blockState) -> {
        return blockState.equals(PixelmonBlocks.poke_chest);
    }, true),
    GIFT_ONLY("gift", (state) -> {
        return state.equals(PixelmonBlocks.poke_gift);
    }, true),
    OFF("", (blockState) -> {
        return false;
    }, false);

    private final String statusText;
    private final Predicate<BlockState> validBlockTest;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("lootballfinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.LOOT_BALL;
    }

    public void next() {
        switch (this) {
            case ALL:
                PUSettings.setLootBallFinderMode(MATER_ONLY);
                break;
            case MATER_ONLY:
                PUSettings.setLootBallFinderMode(BEAST_ONLY);
                break;
            case BEAST_ONLY:
                PUSettings.setLootBallFinderMode(ULTRA_ONLY);
                break;
            case ULTRA_ONLY:
                PUSettings.setLootBallFinderMode(GIFT_ONLY);
                break;
            case GIFT_ONLY:
                PUSettings.setLootBallFinderMode(POKE_ONLY);
                break;
            case POKE_ONLY:
                PUSettings.setLootBallFinderMode(OFF);
                break;
            case OFF:
                PUSettings.setLootBallFinderMode(ALL);
        }

    }

    private LootBallFinderMode(String statusText, Predicate validBlockTest, boolean enabled) {
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

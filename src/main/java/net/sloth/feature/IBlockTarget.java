package net.sloth.feature;

import java.util.function.Predicate;
import net.minecraft.block.BlockState;

public interface IBlockTarget extends IFeatureStatus {
    Predicate<BlockState> getValidBlockTest();
}

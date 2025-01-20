package net.sloth.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.pixelmonmod.pixelmon.api.battles.BattleMode;
import com.pixelmonmod.pixelmon.client.gui.battles.battleScreens.BattleScreen;
import com.pixelmonmod.pixelmon.client.gui.battles.battleScreens.ChooseAttack;
import net.sloth.handler.PUAutobattleHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        value = {ChooseAttack.class},
        remap = false
)
public abstract class MixinChooseAttackScreen extends BattleScreen {
    public MixinChooseAttackScreen(com.pixelmonmod.pixelmon.client.gui.battles.BattleScreen parent, BattleMode mode) {
        super(parent, mode);
    }

    @Inject(
            method = {"render"},
            at = {@At("RETURN")}
    )
    private void onRenderAttackScreen(MatrixStack matrix, int width, int height, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        PUAutobattleHandler.onCallback(this.bm, this.parent);
    }
}

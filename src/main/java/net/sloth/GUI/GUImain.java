package net.sloth.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.sloth.feature.*;
import net.sloth.util.PUSettings;

import static net.sloth.util.PUSettings.getModeText;

public class GUImain extends Screen {
    // 버튼 참조
    private Button modeButton;

    public GUImain() {
        super(new StringTextComponent("Custom GUI"));
    }

    @Override
    protected void init() {

        modeButton = this.addButton(new Button(
                180,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getArcChaliceFinderMode())),
                button -> {

                    ArcChaliceFinderMode currentMode = PUSettings.getArcChaliceFinderMode();
                    currentMode.next();

                    ArcChaliceFinderMode newMode = PUSettings.getArcChaliceFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                360,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getBirdTempleFinderMode())),
                button -> {

                    BirdTempleFinderMode currentMode = PUSettings.getBirdTempleFinderMode();
                    currentMode.next();

                    BirdTempleFinderMode newMode = PUSettings.getBirdTempleFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                540,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getBossFinderMode())),
                button -> {

                    BossFinderMode currentMode = PUSettings.getBossFinderMode();
                    currentMode.next();

                    BossFinderMode newMode = PUSettings.getBossFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                720,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getFossilFinderMode())),
                button -> {
                    FossilFinderMode currentMode = PUSettings.getFossilFinderMode();
                    currentMode.next();

                    FossilFinderMode newMode = PUSettings.getFossilFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                720,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getLegiFinderMode())),
                button -> {
                    LegiFinderMode currentMode = PUSettings.getLegiFinderMode();
                    currentMode.next();

                    LegiFinderMode newMode = PUSettings.getLegiFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                720,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getRaidFinderMode())),
                button -> {
                    RaidFinderMode currentMode = PUSettings.getRaidFinderMode();
                    currentMode.next();

                    RaidFinderMode newMode = PUSettings.getRaidFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                720,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getShinyFinderMode())),
                button -> {
                    ShinyFinderMode currentMode = PUSettings.getShinyFinderMode();
                    currentMode.next();

                    ShinyFinderMode newMode = PUSettings.getShinyFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                720,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getUltraBeastFinderMode())),
                button -> {
                    UltraBeastFinderMode currentMode = PUSettings.getUltraBeastFinderMode();
                    currentMode.next();

                    UltraBeastFinderMode newMode = PUSettings.getUltraBeastFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));

        modeButton = this.addButton(new Button(
                720,
                120,
                150,
                20,
                new StringTextComponent(getModeText(PUSettings.getWormholeFinderMode())),
                button -> {
                    WormholeFinderMode currentMode = PUSettings.getWormholeFinderMode();
                    currentMode.next();

                    WormholeFinderMode newMode = PUSettings.getWormholeFinderMode();

                    button.setMessage(new StringTextComponent(getModeText(newMode)));
                }
        ));
    }





    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        drawCenteredString(
                matrixStack,
                this.font,
                "LootBallFinder Mode",
                255,
                100,
                0xFFFFFF
        );

        drawCenteredString(
                matrixStack,
                this.font,
                "ArcChaliceFinder Mode",
                435,
                100,
                0xFFFFFF
        );

        drawCenteredString(
                matrixStack,
                this.font,
                "AutoBattle Mode",
                625,
                100,
                0xFFFFFF
        );

        drawCenteredString(
                matrixStack,
                this.font,
                "AutoBattle Mode",
                795,
                100,
                0xFFFFFF
        );


        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}

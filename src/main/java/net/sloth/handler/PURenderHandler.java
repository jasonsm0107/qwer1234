package net.sloth.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.pixelmonmod.pixelmon.api.util.helpers.NetworkHelper;
import com.pixelmonmod.pixelmon.client.gui.ItemDropsScreen;
import com.pixelmonmod.pixelmon.client.gui.battles.BattleScreen;
import com.pixelmonmod.pixelmon.client.gui.battles.battleScreens.BattleBaseScreen;
import com.pixelmonmod.pixelmon.client.gui.battles.battleScreens.screens.LevelUpScreen;
import com.pixelmonmod.pixelmon.comm.packetHandlers.itemDrops.ServerItemDropPacket;
import com.pixelmonmod.pixelmon.comm.packetHandlers.itemDrops.ServerItemDropPacket.PacketMode;
import com.pixelmonmod.pixelmon.entities.pixelmon.drops.DroppedItem;
//import net.sloth.command.LootFilterCommand;
import net.sloth.config.PUConfig;
import net.sloth.feature.IFeatureStatus;
import net.sloth.mixin.ItemDropScreenAccessor;
import net.sloth.util.PUSettings;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "ingka1111",
        bus = Bus.FORGE,
        value = {Dist.CLIENT}
)
public class PURenderHandler {
    public PURenderHandler() {
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post e) {
        if (e.getType().equals(ElementType.HOTBAR)) {
            FontRenderer font = Minecraft.getInstance().font;
            boolean x = true;
            int y = 2;
            y = renderFeatureStatus(PUSettings.getZygardeLocationMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getLegiFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getUltraBeastFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getPokedexHelperMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getLootBallFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getShinyFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getAutoFisherMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getAutoBattleMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getRaidFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getBossFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getBirdTempleFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getIlexShrineFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getArcChaliceFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getChestFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getNpcFinderMode(), 2, y, e.getMatrixStack(), font);
            y = renderFeatureStatus(PUSettings.getFossilFinderMode(), 2, y, e.getMatrixStack(), font);
            renderFeatureStatus(PUSettings.getWormholeFinderMode(), 2, y, e.getMatrixStack(), font);
        }
    }

    private static int renderFeatureStatus(IFeatureStatus status, int x, int y, MatrixStack matrixStack, FontRenderer font) {
        TranslationTextComponent text = status.getStatusText();
        if (text == null) {
            return y;
        } else {
            font.draw(matrixStack, text, (float)x, (float)y, status.getColor().getRGB());
            font.getClass();
            y += 9;
            return y;
        }
    }

    @SubscribeEvent
    public static void onRenderBattleScreen(GuiScreenEvent.DrawScreenEvent e) {
        if (e.getGui() instanceof BattleScreen) {
            BattleScreen screen = (BattleScreen)e.getGui();
            if ((Boolean)PUConfig.INSTANCE.autoAcknowledge.get()) {
                try {
                    Field currentScreenField = BattleScreen.class.getDeclaredField("currentScreen");
                    currentScreenField.setAccessible(true);
                    BattleBaseScreen battleBaseScreen = (BattleBaseScreen)currentScreenField.get(screen);
                    if (battleBaseScreen instanceof LevelUpScreen) {
                        battleBaseScreen.click(0, 0, 0.0, 0.0);
                    } else {
                        screen.battleLog.acknowledge();
                    }
                } catch (IllegalAccessException | NoSuchFieldException var4) {
                    ReflectiveOperationException ex = var4;
                    ((ReflectiveOperationException)ex).printStackTrace();
                }
            }

        }
    }

    @SubscribeEvent
    public static void onRenderPixelmonLevelUpScreen(GuiScreenEvent.DrawScreenEvent e) {
    }

    @SubscribeEvent
    public static void onRenderLootScreen(GuiScreenEvent.DrawScreenEvent e) {
        if (e.getGui() instanceof ItemDropsScreen) {
            /*if (LootFilterCommand.isFiltered()) {
                ItemDropsScreen screen = (ItemDropsScreen)e.getGui();
                DroppedItem[] var2 = ((ItemDropScreenAccessor)screen).getDrops().items;
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    DroppedItem item = var2[var4];
                    if (LootFilterCommand.shouldTakeItem(item.item.getItem())) {
                        NetworkHelper.sendToServer(new ServerItemDropPacket(item.id));
                    }
                }

                NetworkHelper.sendToServer(new ServerItemDropPacket(PacketMode.DropAllItems));
                screen.func_231175_as__();
            }*/
        }
    }
}

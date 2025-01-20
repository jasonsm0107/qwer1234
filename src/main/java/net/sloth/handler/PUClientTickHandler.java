/*package net.sloth.handler;

import com.pixelmonmod.pixelmon.entities.projectiles.HookEntity;
import com.pixelmonmod.pixelmon.items.FishingRodItem;
//import net.sloth.command.FishingCommand;
import net.sloth.config.PUConfig;
import net.sloth.feature.AutoFisherMode;
import net.sloth.mixin.MinecraftAccessor;
import net.sloth.util.PUBattleUtil;
import net.sloth.util.PUSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "ingka1111",
        bus = Bus.FORGE,
        value = {Dist.CLIENT}
)
public class PUClientTickHandler {
    //private static int remainingDelayTicks = 0;
    private static int searchThreadCheckDelayTicks = 0;

    public PUClientTickHandler() {
    }

    @SubscribeEvent
    public static void autoFisherCheck(TickEvent.ClientTickEvent e) {
        if (e.phase.equals(Phase.END)) {
            if (!PUSettings.getAutoFisherMode().equals(AutoFisherMode.OFF)) {
                if (remainingDelayTicks-- <= 0) {
                    ClientPlayerEntity player = Minecraft.getInstance().player;
                    if (player != null) {
                        if (player.getMainHandItem().getItem() instanceof FishingRodItem) {
                            if (PUBattleUtil.isOutOfBattle()) {
                                remainingDelayTicks = (Integer)PUConfig.INSTANCE.autoFisherUpdateTickDelay.get();
                                if (player.fishing == null) {
                                    Minecraft.getInstance().execute(() -> {
                                        ((MinecraftAccessor)Minecraft.getInstance()).rightClick();
                                    });
                                } else if (player.fishing instanceof HookEntity) {
                                    HookEntity entity = (HookEntity)player.fishing;
                                    int state = (Integer)entity.getEntityData().get(HookEntity.DATA_HOOK_STATE);
                                    /*if (state >= FishingCommand.getMinimalMarks()) {
                                        Minecraft.getInstance().execute(() -> {
                                            ((MinecraftAccessor)Minecraft.getInstance()).rightClick();
                                        });
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void searchThreadCheck(TickEvent.ClientTickEvent e) {
        if (e.phase.equals(Phase.END)) {
            /*if (searchThreadCheckDelayTicks-- <= 0) {
                searchThreadCheckDelayTicks = 60;
                if (!PUTaskHandler.isSearchTaskRunning()) {
                }

            }
        }
    }*/


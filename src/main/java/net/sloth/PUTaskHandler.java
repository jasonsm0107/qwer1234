package net.sloth;

import net.sloth.NativeBridge;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "ingka1111")
public class PUTaskHandler {
    private static boolean isInitialized = false;
    private static final Object lockObject = new Object(); // 스레드 안전성을 위한 락 객체 추가

    public static void init() {
        synchronized (lockObject) {
            if (!isInitialized) {
                try {
                    NativeBridge.initializeTasks();
                    isInitialized = true;
                } catch (Exception e) {
                    ErrorHandler.handleNativeError("Task initialization", e);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (isInitialized && event.phase == TickEvent.Phase.END &&
                Minecraft.getInstance().level != null) {
            try {
                NativeBridge.updateTasks();
            } catch (Exception e) {
                ErrorHandler.handleNativeError("Task update", e);
            }
        }
    }

    public static void cleanup() {
        synchronized (lockObject) {
            if (isInitialized) {
                try {
                    NativeBridge.cleanupTasks();
                    isInitialized = false;
                } catch (Exception e) {
                    ErrorHandler.handleNativeError("Task cleanup", e);
                }
            }
        }
    }
}
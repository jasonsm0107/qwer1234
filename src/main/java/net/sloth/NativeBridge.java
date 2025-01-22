package net.sloth;

import journeymap.client.api.IClientAPI;
import net.minecraftforge.eventbus.api.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NativeBridge {
    private static final Logger LOGGER = LogManager.getLogger(NativeBridge.class);
    private static long nativeHandle = 0;  // 네이티브 객체에 대한 포인터

    // 네이티브 메서드 선언
    public static native void initialize();
    public static native void cleanup();
    public static native void registerEventBuses(IEventBus modBus, IEventBus forgeBus);
    public static native void initializeJourneyMapPlugin();


    // 이벤트 처리 메서드
    public static native void handleForgeEvent(Object event);

    // 설정 관련 메서드
    public static native void updateSettings(String settingName, Object value);

    public static native void initializeResources(String configPath);
    public static native void cleanupResources();

    // 유틸리티 메서드
    public static boolean isInitialized() {
        return nativeHandle != 0;
    }

    public static native void handleJourneyMapEvent(Object event);

    // 예외 처리를 위한 헬퍼 메서드
    protected static void throwNativeException(String message) {
        throw new RuntimeException("Native error: " + message);
    }

    public static native void initializeJourneyMapAPI(Object api);

    public static native void cleanupJourneyMap();

    /*public static native void createWaypoint(String name, double x, double y, double z, int dimension, int color);
    public static native void removeWaypointByName(String name);
    public static native void removeAllWaypoints();*/

    public static native void initializeTasks();
    public static native void updateTasks();
    public static native void cleanupTasks();
}
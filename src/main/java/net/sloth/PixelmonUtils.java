package net.sloth;

import net.sloth.util.ModLogger;
import net.sloth.util.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

@Mod("ingka1111")
public class PixelmonUtils {
    private static final Logger LOGGER = LogManager.getLogger(PixelmonUtils.class);
    private static boolean nativeLibraryLoaded = false;

    static {
        try {
            // 운영체제 확인 및 적절한 라이브러리 로드
            String osName = System.getProperty("os.name").toLowerCase();
            String libName;

            if (osName.contains("windows")) {
                libName = "pixelmon_utils_native";
            } else if (osName.contains("linux")) {
                libName = "libpixelmon_utils_native";
            } else if (osName.contains("mac")) {
                libName = "libpixelmon_utils_native";
            } else {
                throw new UnsupportedOperationException("Unsupported operating system: " + osName);
            }

            System.loadLibrary(libName);
            nativeLibraryLoaded = true;
            LOGGER.info("Native library loaded successfully: {}", libName);
        } catch (UnsatisfiedLinkError e) {
            LOGGER.error("Failed to load native library", e);
            throw new RuntimeException("Failed to load native library", e);
        }
    }

    public PixelmonUtils() {
        if (!nativeLibraryLoaded) {
            LOGGER.error("Native library not loaded. Mod initialization aborted.");
            return;
        }

        try {
            // 1. 네이티브 초기화
            NativeBridge.initialize();

            // 2. 리소스 초기화
            ResourceManager.init();

            Path logPath = ResourceManager.getModDirectory().resolve("native.log");
            ModLogger.enableNativeLogging(logPath.toString());

            // 3. 태스크 초기화
            PUTaskHandler.init();

            // 4. Forge 모드 설정
            ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () ->
                    Pair.of(() -> "OHNOES\ud83d\ude31", (remote, isServer) -> true)
            );

            // 5. 설정 등록
            ModLoadingContext.get().registerConfig(Type.CLIENT, PUConfig.SPEC);

            // 6. 이벤트 버스 설정
            IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;

            // 7. 클라이언트 사이드 초기화
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                initializeClient(modBus, forgeBus);
            });

        } catch (Exception e) {
            LOGGER.error("Failed to initialize mod", e);
            throw new RuntimeException("Failed to initialize mod", e);
        }
    }

    private void initializeClient(IEventBus modBus, IEventBus forgeBus) {
        try {
            // EventBridge 등록 추가
            EventBridge eventBridge = new EventBridge();
            MinecraftForge.EVENT_BUS.register(eventBridge);

            // 네이티브 이벤트 리스너 등록
            NativeBridge.registerEventBuses(modBus, forgeBus);

            // JourneyMap 플러그인 초기화
            if (isJourneyMapLoaded()) {
                NativeBridge.initializeJourneyMapPlugin();
            }

            LOGGER.info("Client-side initialization completed successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize client-side components", e);
            throw new RuntimeException("Failed to initialize client-side components", e);
        }
    }

    public static boolean isComplexClientLoaded() {
        return ModList.get().isLoaded("complexclient");
    }

    public static boolean isJourneyMapLoaded() {
        return ModList.get().isLoaded("journeymap");
    }

    public void cleanup() {
        if (nativeLibraryLoaded) {
            try {
                // 순서 최적화: UI -> 태스크 -> 플러그인 -> 리소스 -> 네이티브
                if (PUConfig.showStatusOverlay.get()) {
                    // UI 정리 (오버레이 등)
                    NativeBridge.updateSettings("show_status_overlay", false);
                }

                PUTaskHandler.cleanup();
                PUJourneyMapPlugin.cleanup();
                ResourceManager.cleanup();
                NativeBridge.cleanup();

                LOGGER.info("Native resources cleaned up successfully");
            } catch (Exception e) {
                LOGGER.error("Failed to cleanup native resources", e);
            }
        }
    }
}
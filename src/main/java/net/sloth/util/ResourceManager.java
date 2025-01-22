package net.sloth.util;

import net.sloth.NativeBridge;
import net.sloth.ErrorHandler;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceManager {
    private static final Logger LOGGER = LogManager.getLogger(ResourceManager.class);
    private static final Path MOD_DIR = FMLPaths.GAMEDIR.get().resolve("config/pixelmon_utils");
    private static boolean isInitialized = false;

    public static void init() {
        if (!isInitialized) {
            try {
                createDirectories();
                NativeBridge.initializeResources(MOD_DIR.toString());
                isInitialized = true;
                LOGGER.info("Resource manager initialized successfully");
            } catch (Exception e) {
                ErrorHandler.handleNativeError("Resource initialization", e);
            }
        }
    }

    private static void createDirectories() throws Exception {
        if (!Files.exists(MOD_DIR)) {
            Files.createDirectories(MOD_DIR);
        }
    }

    public static void cleanup() {
        if (isInitialized) {
            try {
                NativeBridge.cleanupResources();
                isInitialized = false;
            } catch (Exception e) {
                ErrorHandler.handleNativeError("Resource cleanup", e);
            }
        }
    }

    public static Path getModDirectory() {
        return MOD_DIR;
    }
}
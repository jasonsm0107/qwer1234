package net.sloth.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.sloth.NativeBridge;

public class ModLogger {
    private static final Logger LOGGER = LogManager.getLogger("PixelmonUtils");
    private static boolean nativeLoggingEnabled = false;

    public static void enableNativeLogging(String logPath) {
        try {
            NativeBridge.initializeLogger(logPath, NativeBridge.LOG_LEVEL_DEBUG);
            nativeLoggingEnabled = true;
            info("Native logging enabled");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize native logging", e);
        }
    }

    public static void error(String message) {
        LOGGER.error(message);
        if (nativeLoggingEnabled) {
            try {
                NativeBridge.error(message);
            } catch (Exception e) {
                LOGGER.error("Failed to write to native log", e);
            }
        }
    }

    public static void warn(String message) {
        LOGGER.warn(message);
        if (nativeLoggingEnabled) {
            try {
                NativeBridge.warn(message);
            } catch (Exception e) {
                LOGGER.error("Failed to write to native log", e);
            }
        }
    }

    public static void info(String message) {
        LOGGER.info(message);
        if (nativeLoggingEnabled) {
            try {
                NativeBridge.info(message);
            } catch (Exception e) {
                LOGGER.error("Failed to write to native log", e);
            }
        }
    }

    public static void debug(String message) {
        LOGGER.debug(message);
        if (nativeLoggingEnabled) {
            try {
                NativeBridge.debug(message);
            } catch (Exception e) {
                LOGGER.error("Failed to write to native log", e);
            }
        }
    }
}
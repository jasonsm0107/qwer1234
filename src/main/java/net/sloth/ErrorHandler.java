package net.sloth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorHandler {
    private static final Logger LOGGER = LogManager.getLogger("PixelmonUtils");

    public static void handleNativeError(String operation, Throwable error) {
        LOGGER.error("Native operation failed: {} - {}", operation, error.getMessage());
        // 필요한 경우 사용자에게 알림
    }

    public static void logDebug(String message) {
        LOGGER.debug(message);
    }
}
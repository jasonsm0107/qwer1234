package net.sloth;

import com.google.common.collect.Lists;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.Waypoint;
import journeymap.client.api.event.ClientEvent;
import net.sloth.NativeBridge;
import net.sloth.ErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArrayList;

@journeymap.client.api.ClientPlugin
public class PUJourneyMapPlugin implements IClientPlugin {
    private static final Logger LOGGER = LogManager.getLogger(PUJourneyMapPlugin.class);

    @Override
    public void initialize(IClientAPI api) {
        try {
            // API 객체를 네이티브 라이브러리로 전달
            NativeBridge.initializeJourneyMapAPI(api);
            LOGGER.info("JourneyMap plugin initialized successfully");
        } catch (Exception e) {
            ErrorHandler.handleNativeError("JourneyMap initialization", e);
        }
    }

    @Override
    public String getModId() {
        return "ingka1111";
    }

    @Override
    public void onEvent(ClientEvent event) {
        try {
            NativeBridge.handleJourneyMapEvent(event);
        } catch (Exception e) {
            ErrorHandler.handleNativeError("JourneyMap event", e);
        }
    }

    public static void cleanup() {
        try {
            NativeBridge.cleanupJourneyMap();
        } catch (Exception e) {
            ErrorHandler.handleNativeError("JourneyMap cleanup", e);
        }
    }
}
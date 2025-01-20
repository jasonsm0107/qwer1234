package net.sloth.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sloth.storage.adapter.BlockPosTypeAdapter;
import net.sloth.storage.adapter.ResourceLocationTypeAdapter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.FolderName;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StorageManager {
    private static final Logger log = LogManager.getLogger(StorageManager.class);
    private static final Object $LOCK = new Object[0];
    private static final Gson gson = (new GsonBuilder()).registerTypeAdapter(BlockPos.class, new BlockPosTypeAdapter()).registerTypeAdapter(ResourceLocation.class, new ResourceLocationTypeAdapter()).enableComplexMapKeySerialization().create();
    private static Path storagePath;
    private static PUStorage storage;

    public StorageManager() {
    }

    public static void init() {
        Minecraft minecraft = Minecraft.getInstance();
        log.debug("Initializing Storage...");
        if (minecraft.isLocalServer()) {
            log.debug("Detected Local Server");
            IntegratedServer server = minecraft.getSingleplayerServer();
            if (server == null) {
                log.error("Could not load IntegratedServer!");
                return;
            }

            storagePath = server.getWorldPath(FolderName.ROOT).resolve("pixelmonutils").resolve("storage.json");
        } else {
            log.debug("Detected Remote Server");
            if (minecraft.getCurrentServer() == null) {
                log.error("Could not load Server Data!");
                return;
            }

            storagePath = FileUtils.getUserDirectory().toPath().resolve(".minecraft").resolve("pixelmonutils").resolve(minecraft.getCurrentServer().ip.replaceAll(":", "_")).resolve("storage.json");
        }

        if (storagePath.getParent().toFile().mkdirs()) {
            log.debug("Generated {} folder", storagePath.getParent().toFile().getAbsolutePath());
        }

        if (storagePath.toFile().exists()) {
            try {
                Reader reader = Files.newBufferedReader(storagePath);
                setStorage((PUStorage)gson.fromJson(reader, PUStorage.class));
                ((Reader)reader).close();
            } catch (IOException var2) {
                IOException e = var2;
                log.fatal("Could not load Storage file!", e);
                if (storagePath.toFile().delete()) {
                    log.info("Deleted {}", storagePath);
                }

                setStorage(initializeStorage());
            }
        } else {
            setStorage(initializeStorage());
        }

    }

    public static void save() {
        if (storagePath == null) {
            log.warn("Storage Path is null!");
        } else if (storage == null) {
            log.warn("Could not find storage object for Path {}", storagePath.toString());
        } else {
            try {
                log.debug("Saving Storage File...");
                FileWriter writer = new FileWriter(storagePath.toFile(), false);
                gson.toJson(storage, writer);
                writer.flush();
                writer.close();
            } catch (IOException var1) {
                IOException e = var1;
                log.fatal("Could not save Storage file!", e);
                if (storagePath.toFile().delete()) {
                    log.info("Deleted {}", storagePath);
                }
            }

        }
    }

    private static PUStorage initializeStorage() {
        PUStorage puStorage = new PUStorage();
        puStorage.setKnownLootBalls(new HashMap());
        return puStorage;
    }

    private static void setStorage(PUStorage storage) {
        synchronized($LOCK) {
            StorageManager.storage = storage;
        }
    }

    public static PUStorage getStorage() {
        synchronized($LOCK) {
            return storage;
        }
    }
}

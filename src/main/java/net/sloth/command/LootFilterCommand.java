/*package net.sloth.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.sloth.command.argument.FilenameArgument;
import net.sloth.config.PUConfig;
import net.sloth.util.PUChatUtil;
import net.sloth.util.PUTranslation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootFilterCommand extends AbstractClientCommand {
    private static final Logger log = LogManager.getLogger("pixelmonutils/LootFilter");
    private static final Object $LOCK = new Object[0];
    public static final Path ITEM_LIST_CONFIGS;
    public static final CopyOnWriteArrayList<Item> itemWhitelist;
    private static boolean filtered;
    private static boolean takeAll;

    public static boolean shouldTakeItem(Item item) {
        return isTakeAll() ? true : itemWhitelist.contains(item);
    }

    private static void enabledLoadUp() {
        if ((Boolean)PUConfig.INSTANCE.enableLootFilterOnStartup.get()) {
            log.info("Enable LootFilter");
            setFiltered(true);
        }
    }

    private static void fileLoadUp() {
        String config = (String)PUConfig.INSTANCE.loadLootFilterOnStartup.get();
        if (config != null) {
            if (!config.isEmpty()) {
                if (!config.endsWith(".txt")) {
                    config = config + ".txt";
                }

                File file = ITEM_LIST_CONFIGS.resolve(config).toFile();
                if (!file.exists()) {
                    PUConfig.INSTANCE.loadLootFilterOnStartup.set("");
                    log.warn("Skipped LootFiler file {}. File does not exist!", file.toPath().toString());
                } else {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));

                        String registryName;
                        while((registryName = reader.readLine()) != null) {
                            itemWhitelist.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName)));
                        }

                        reader.close();
                        log.info("Loaded {} entries from LootFilter file {} on startup", itemWhitelist.size(), config);
                    } catch (IOException var4) {
                        IOException e = var4;
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public LootFilterCommand() {
        super("lootfilter");
        this.executes((context) -> {
            if (isFiltered()) {
                PUChatUtil.chat(PUTranslation.of("lootfilter.disabled", new Object[0]));
                setFiltered(false);
            } else {
                PUChatUtil.chat(PUTranslation.of("lootfilter.enabled", new Object[0]));
                setFiltered(true);
            }

            return success();
        });
        this.then(Commands.literal("toggleTakeAll").executes((context) -> {
            setTakeAll(!isTakeAll());
            if (isTakeAll()) {
                ((CommandSource)context.getSource()).sendSuccess(PUTranslation.of("lootfilter.takeall.on", new Object[0]), false);
            } else {
                ((CommandSource)context.getSource()).sendSuccess(PUTranslation.of("lootfilter.takeall.off", new Object[0]), false);
            }

            return 1;
        }));
        this.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("whitelist").then(Commands.literal("add").then(Commands.argument("item", ItemArgument.item()).executes((context) -> {
            Item item = ItemArgument.getItem(context, "item").getItem();
            itemWhitelist.add(item);
            PUChatUtil.chat(PUTranslation.of("lootfilter.whitelist.added", new Object[]{item.getName(new ItemStack(item))}));
            return 1;
        })))).then(Commands.literal("remove").then(Commands.argument("item", ItemArgument.item()).executes((context) -> {
            Item item = ItemArgument.getItem(context, "item").getItem();
            itemWhitelist.removeIf((item1) -> {
                return item1.equals(item);
            });
            PUChatUtil.chat(PUTranslation.of("lootfilter.whitelist.removed", new Object[]{item.getName(new ItemStack(item))}));
            return 1;
        })))).then(Commands.literal("export").then(Commands.argument("fileName", StringArgumentType.word()).executes((context) -> {
            String fileName = StringArgumentType.getString(context, "fileName");

            try {
                Path storagePath = ITEM_LIST_CONFIGS;
                storagePath.toFile().mkdirs();
                FileWriter writer = new FileWriter(storagePath.resolve(fileName + ".txt").toFile());
                Iterator var4 = itemWhitelist.iterator();

                while(var4.hasNext()) {
                    Item item = (Item)var4.next();
                    writer.write(item.getRegistryName().toString() + System.lineSeparator());
                }

                writer.close();
                PUChatUtil.chat(PUTranslation.of("lootfilter.whitelist.exported", new Object[]{itemWhitelist.size()}));
                return 1;
            } catch (IOException var6) {
                IOException e = var6;
                throw new RuntimeException(e);
            }
        })))).then(Commands.literal("import").then(Commands.argument("fileName", FilenameArgument.filenameArgument(ITEM_LIST_CONFIGS)).executes((context) -> {
            File file = FilenameArgument.getFile(context, "fileName");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String registryName;
                while((registryName = reader.readLine()) != null) {
                    itemWhitelist.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName)));
                }

                reader.close();
                PUChatUtil.chat(PUTranslation.of("lootfilter.whitelist.imported", new Object[]{itemWhitelist.size()}));
                return 1;
            } catch (IOException var4) {
                IOException e = var4;
                throw new RuntimeException(e);
            }
        })))).then(Commands.literal("clear").executes((context) -> {
            itemWhitelist.clear();
            PUChatUtil.chat(PUTranslation.of("lootfilter.whitelist.cleared", new Object[0]));
            return 1;
        }))).executes((context) -> {
            IFormattableTextComponent component = new StringTextComponent("Whitelisted Items:\n");
            itemWhitelist.forEach((item) -> {
                component.append(item.getName(new ItemStack(item))).append("\n");
            });
            component.append("\nTotal: " + itemWhitelist.size());
            PUChatUtil.chat(component);
            return 1;
        }));
    }

    public static boolean isFiltered() {
        synchronized($LOCK) {
            return filtered;
        }
    }

    private static void setFiltered(boolean filtered) {
        synchronized($LOCK) {
            LootFilterCommand.filtered = filtered;
        }
    }

    private static boolean isTakeAll() {
        synchronized($LOCK) {
            return takeAll;
        }
    }

    private static void setTakeAll(boolean takeAll) {
        synchronized($LOCK) {
            LootFilterCommand.takeAll = takeAll;
        }
    }

    static {
        ITEM_LIST_CONFIGS = FMLPaths.CONFIGDIR.get().resolve("pixelmonutils").resolve("itemlist");
        itemWhitelist = new CopyOnWriteArrayList();
        filtered = false;
        takeAll = false;
        fileLoadUp();
        enabledLoadUp();
    }
}*/
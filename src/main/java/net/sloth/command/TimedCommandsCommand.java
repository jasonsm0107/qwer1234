/*package net.sloth.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.datafixers.util.Pair;
import net.sloth.util.PUBattleUtil;
import net.sloth.util.PUChatUtil;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.command.Commands;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TimedCommandsCommand extends AbstractClientCommand {
    private static final ConcurrentHashMap<String, Pair<Integer, Integer>> commands = new ConcurrentHashMap();
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static final ScheduledFuture<?> task;

    public TimedCommandsCommand() {
        super("timedCommands");
        this.then(Commands.literal("add").then(Commands.argument("seconds", IntegerArgumentType.integer(5, 600)).then(Commands.argument("command", StringArgumentType.string()).executes((context) -> {
            int seconds = IntegerArgumentType.getInteger(context, "seconds");
            String command = StringArgumentType.getString(context, "command");
            if (command.isEmpty()) {
                return success();
            } else {
                if (!command.startsWith("/")) {
                    command = "/" + command;
                }

                commands.put(command, Pair.of(seconds, seconds));
                return 1;
            }
        }))));
        this.then(Commands.literal("remove").then(Commands.argument("command", StringArgumentType.string()).executes((context) -> {
            String command = StringArgumentType.getString(context, "command");
            if (!command.startsWith("/")) {
                command = "/" + command;
            }

            commands.remove(command);
            return 1;
        })));
        this.executes((context) -> {
            IFormattableTextComponent component = new StringTextComponent("Active Commands:\n");
            commands.forEach((s, integerIntegerPair) -> {
                component.append(s).append("\n");
            });
            PUChatUtil.chat(component);
            return success();
        });
    }

    public static void reset() {
        commands.clear();
    }

    static {
        task = service.scheduleWithFixedDelay(() -> {
            commands.forEach((s, pair) -> {
                try {
                    int remainingDuration = (Integer)pair.getSecond();
                    --remainingDuration;
                    if (remainingDuration <= 0) {
                        Minecraft.getInstance().execute(() -> {
                            if (PUBattleUtil.isOutOfBattle()) {
                                Minecraft.getInstance().player.chat(s);
                            }

                        });
                        remainingDuration = (Integer)pair.getFirst();
                    }

                    commands.put(s, Pair.of(pair.getFirst(), remainingDuration));
                } catch (Exception var3) {
                    Exception ex = var3;
                    ex.printStackTrace();
                }

            });
        }, 0L, 1L, TimeUnit.SECONDS);
    }
}*/

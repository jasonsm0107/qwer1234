/*package net.sloth.command;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Pair;
import net.sloth.journaymap.PUJourneyMapPlugin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResetPokemonCommand extends AbstractClientCommand {
    private static final Logger log = LogManager.getLogger(ResetPokemonCommand.class);
    private static final Object $LOCK = new Object[0];
    private static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
    private static ScheduledFuture<?> repeatingTask;
    private static int backCommandDelay = 5;

    public ResetPokemonCommand() {
        super("reset");
        Multimap<String, PreCheck> preChecks = ArrayListMultimap.create();
        preChecks.put("pokedexAware", this.uncaught());
        preChecks.put("findAware", this.findForms());
        preChecks.put("findAware", this.findPalette());
        preChecks.put("legendaryAware", this.legendary());
        preChecks.put("legendaryAware", this.ultraBeast());
        List<LiteralArgumentBuilder<CommandSource>> preCheckArguments = new ArrayList();

        LiteralArgumentBuilder root;
        label49:
        for(Iterator var3 = preChecks.keySet().iterator(); var3.hasNext(); preCheckArguments.add(root)) {
            String key = (String)var3.next();
            root = (LiteralArgumentBuilder)Commands.literal(key).executes((context) -> {
                return this.start(context, preChecks.get(key));
            });
            Iterator var6 = preChecks.keySet().iterator();

            while(true) {
                String argKey;
                do {
                    if (!var6.hasNext()) {
                        continue label49;
                    }

                    argKey = (String)var6.next();
                } while(argKey.equals(key));

                String finalArgKey = argKey;
                LiteralArgumentBuilder<CommandSource> arg1 = (LiteralArgumentBuilder)Commands.literal(argKey).executes((context) -> {
                    return this.start(context, preChecks.get(key), preChecks.get(finalArgKey));
                });
                Iterator var9 = preChecks.keySet().iterator();

                while(var9.hasNext()) {
                    String argKey2 = (String)var9.next();
                    if (!argKey2.equals(key) && !argKey2.equals(argKey)) {
                        String finalArgKey1 = argKey;
                        LiteralArgumentBuilder<CommandSource> arg2 = (LiteralArgumentBuilder)Commands.literal(argKey2).executes((context) -> {
                            return this.start(context, preChecks.get(key), preChecks.get(finalArgKey1), preChecks.get(argKey2));
                        });
                        arg1.then(arg2);
                    }
                }

                root.then(arg1);
            }
        }

        RequiredArgumentBuilder<CommandSource, Integer> delayLiteral = (RequiredArgumentBuilder)Commands.argument("seconds", IntegerArgumentType.integer(20)).executes((x$0) -> {
            return this.start(x$0);
        });
        Iterator var13 = preCheckArguments.iterator();

        while(var13.hasNext()) {
            root = (LiteralArgumentBuilder)var13.next();
            delayLiteral.then(root);
        }

        this.then(Commands.argument("awayCommand", StringArgumentType.string()).then(((RequiredArgumentBuilder)Commands.argument("backCommand", StringArgumentType.string()).executes((context) -> {
            String awayCommand = StringArgumentType.getString(context, "awayCommand").startsWith("/") ? StringArgumentType.getString(context, "awayCommand") : "/" + StringArgumentType.getString(context, "awayCommand");
            String backCommand = StringArgumentType.getString(context, "backCommand").startsWith("/") ? StringArgumentType.getString(context, "backCommand") : "/" + StringArgumentType.getString(context, "backCommand");
            this.chat(awayCommand);
            executor.schedule(() -> {
                this.chat(backCommand);
            }, (long)backCommandDelay, TimeUnit.SECONDS);
            return success();
        })).then(Commands.literal("delay").then(delayLiteral))));
        this.then(Commands.literal("stop").executes((context) -> {
            if (getRepeatingTask() == null) {
                return success();
            } else {
                getRepeatingTask().cancel(true);
                setRepeatingTask((ScheduledFuture)null);
                ((CommandSource)context.getSource()).sendSuccess(new StringTextComponent("Stopped command loop."), false);
                return success();
            }
        }));
        this.then(Commands.literal("backCommandDelay").then(Commands.argument("seconds", IntegerArgumentType.integer(5)).executes((context) -> {
            backCommandDelay = IntegerArgumentType.getInteger(context, "seconds");
            return success();
        })));
    }

    private PreCheck uncaught() {
        return () -> {
            return Pair.of(() -> {
                return !PUJourneyMapPlugin.POKEDEX_HELPER.getWaypoints().isEmpty();
            }, "Skipped reset since a uncaught Pokemon has been found!");
        };
    }

    private PreCheck legendary() {
        return () -> {
            return Pair.of(() -> {
                return !PUJourneyMapPlugin.LEGENDARY_FINDER.getWaypoints().isEmpty();
            }, "Skipped reset since a legendary Pokemon has been found!");
        };
    }

    private PreCheck ultraBeast() {
        return () -> {
            return Pair.of(() -> {
                return !PUJourneyMapPlugin.ULTRA_BEASTS_FINDER.getWaypoints().isEmpty();
            }, "Skipped reset since a ultra beast has been found!");
        };
    }

    private PreCheck findForms() {
        return () -> {
            return Pair.of(() -> {
                return !PUJourneyMapPlugin.FIND_COMMAND_FORMS.getWaypoints().isEmpty();
            }, "Skipped reset since a searched Pokemon has been found!");
        };
    }

    private PreCheck findPalette() {
        return () -> {
            return Pair.of(() -> {
                return !PUJourneyMapPlugin.FIND_COMMAND_PALETTE.getWaypoints().isEmpty();
            }, "Skipped reset since a searched Pokemon has been found!");
        };
    }

    private int start(CommandContext<CommandSource> context, Collection<PreCheck> checks) {
        if (getRepeatingTask() != null) {
            ((CommandSource)context.getSource()).sendSuccess(new StringTextComponent("Please cancel the currently running task first!"), false);
            return success();
        } else {
            String awayCommand = StringArgumentType.getString(context, "awayCommand").startsWith("/") ? StringArgumentType.getString(context, "awayCommand") : "/" + StringArgumentType.getString(context, "awayCommand");
            String backCommand = StringArgumentType.getString(context, "backCommand").startsWith("/") ? StringArgumentType.getString(context, "backCommand") : "/" + StringArgumentType.getString(context, "backCommand");
            int delay = IntegerArgumentType.getInteger(context, "seconds");
            setRepeatingTask(executor.scheduleWithFixedDelay(() -> {
                Iterator var5 = checks.iterator();

                Pair result;
                do {
                    if (!var5.hasNext()) {
                        this.chat(awayCommand);

                        try {
                            executor.schedule(() -> {
                                this.chat(backCommand);
                            }, (long)backCommandDelay, TimeUnit.SECONDS).get();
                        } catch (ExecutionException | InterruptedException var8) {
                            Exception e = var8;
                            log.trace(e);
                        }

                        return;
                    }

                    PreCheck check = (PreCheck)var5.next();
                    result = check.validate();
                } while(!(Boolean)((Supplier)result.getFirst()).get());

                ((CommandSource)context.getSource()).sendSuccess(new StringTextComponent((String)result.getSecond()), false);
            }, 0L, (long)delay, TimeUnit.SECONDS));
            return success();
        }
    }

    @SafeVarargs
    private final int start(CommandContext<CommandSource> context, Collection<PreCheck> checks, Collection<PreCheck>... other) {
        List<PreCheck> preChecks = new ArrayList(checks);
        Collection[] var5 = other;
        int var6 = other.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Collection<PreCheck> otherChecks = var5[var7];
            preChecks.addAll(otherChecks);
        }

        return this.start(context, (Collection)preChecks);
    }

    private int start(CommandContext<CommandSource> context, PreCheck... checks) {
        return this.start(context, (Collection)Arrays.asList(checks));
    }

    public static void reset() {
        if (getRepeatingTask() != null) {
            getRepeatingTask().cancel(true);
            setRepeatingTask((ScheduledFuture)null);
        }

    }

    public static ScheduledFuture<?> getRepeatingTask() {
        synchronized($LOCK) {
            return repeatingTask;
        }
    }

    public static void setRepeatingTask(ScheduledFuture<?> repeatingTask) {
        synchronized($LOCK) {
            ResetPokemonCommand.repeatingTask = repeatingTask;
        }
    }

    @FunctionalInterface
    private interface PreCheck {
        Pair<Supplier<Boolean>, String> validate();
    }
}*/

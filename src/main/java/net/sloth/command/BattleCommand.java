/*package net.sloth.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import net.sloth.command.argument.PokemonArgument;
import net.sloth.config.PUConfig;
import net.sloth.util.PUChatUtil;
import net.sloth.util.PUTranslation;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BattleCommand extends AbstractClientCommand {
    private static final Logger log = LogManager.getLogger("pixelmonutils/BattleBlacklist");

    public BattleCommand() {
        super("battle");
        this.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("blacklist").executes((context) -> {
            StringBuilder pokemonNames = new StringBuilder();
            getBlacklist().forEach((species) -> {
                pokemonNames.append("\n").append(species.getLocalizedName());
            });
            PUChatUtil.chat(PUTranslation.of("autobattle.blacklist", new Object[]{pokemonNames.toString().isEmpty() ? PUTranslation.of("command.find.list.empty", new Object[0]) : pokemonNames.toString()}));
            return 1;
        })).then(Commands.literal("add").then(Commands.argument("pokemon", PokemonArgument.pokemon()).executes((context) -> {
                    Species pokemonType = PokemonArgument.getPokemon(context, "pokemon");
                    addToBlacklist(pokemonType);
                    PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.added", new Object[]{pokemonType.getTranslatedName()}));
                    return 1;
                })))
        ).then(Commands.literal("remove").then(Commands.argument("pokemon", PokemonArgument.pokemon()).executes((context) -> {
            Species pokemonType = PokemonArgument.getPokemon(context, "pokemon");
            if (removeFromBlacklist(pokemonType)) {
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.removed", new Object[]{pokemonType.getTranslatedName()}));
            } else {
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.removed.unknown", new Object[]{pokemonType.getTranslatedName()}));
            }

            return 1;
        })))).then(Commands.literal("clear").executes((context) -> {
            clearBlacklist();
            PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.cleared", new Object[0]));
            return 1;
        }))).then(Commands.literal("shiny").executes((context) -> {
            if ((Boolean)PUConfig.INSTANCE.pauseOnShinys.get()) {
                PUConfig.INSTANCE.pauseOnShinys.set(false);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.shiny.off", new Object[0]).withStyle(TextFormatting.RED));
            } else {
                PUConfig.INSTANCE.pauseOnShinys.set(true);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.shiny.on", new Object[0]).withStyle(TextFormatting.GREEN));
            }

            return 1;
        }))).then(Commands.literal("legendary").executes((context) -> {
            if ((Boolean)PUConfig.INSTANCE.pauseOnLegendaries.get()) {
                PUConfig.INSTANCE.pauseOnLegendaries.set(false);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.legendary.off", new Object[0]).withStyle(TextFormatting.RED));
            } else {
                PUConfig.INSTANCE.pauseOnLegendaries.set(true);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.legendary.on", new Object[0]).withStyle(TextFormatting.GREEN));
            }

            return 1;
        }))).then(Commands.literal("ultraBeasts").executes((context) -> {
            if ((Boolean)PUConfig.INSTANCE.pauseOnUltraBeasts.get()) {
                PUConfig.INSTANCE.pauseOnUltraBeasts.set(false);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.ultrabeasts.off", new Object[0]).withStyle(TextFormatting.RED));
            } else {
                PUConfig.INSTANCE.pauseOnUltraBeasts.set(true);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.ultrabeasts.on", new Object[0]).withStyle(TextFormatting.GREEN));
            }

            return 1;
        }))).then(Commands.literal("uncaught").executes((context) -> {
            if ((Boolean)PUConfig.INSTANCE.pauseOnUncaught.get()) {
                PUConfig.INSTANCE.pauseOnUncaught.set(false);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.uncaught.off", new Object[0]).withStyle(TextFormatting.RED));
            } else {
                PUConfig.INSTANCE.pauseOnUncaught.set(true);
                PUChatUtil.chat(PUTranslation.of("autobattle.blacklist.uncaught.on", new Object[0]).withStyle(TextFormatting.GREEN));
            }

            return 1;
        })));
        this.then(Commands.literal("selfAcknowledge").executes((context) -> {
            boolean invertedState = !(Boolean)PUConfig.INSTANCE.autoAcknowledge.get();
            PUConfig.INSTANCE.autoAcknowledge.set(invertedState);
            PUChatUtil.chat(invertedState ? PUTranslation.of("autobattle.acknowledge.on", new Object[0]) : PUTranslation.of("autobattle.acknowledge.off", new Object[0]));
            return 1;
        }));
    }

    private static void addToBlacklist(Species species) {
        CopyOnWriteArrayList<Species> blacklist = getBlacklist();
        blacklist.add(species);
        setBlackList(blacklist);
    }

    private static boolean removeFromBlacklist(Species species) {
        CopyOnWriteArrayList<Species> blacklist = getBlacklist();
        boolean removed = blacklist.removeIf((species1) -> {
            return species1.is(new Species[]{species});
        });
        setBlackList(blacklist);
        return removed;
    }

    private static void clearBlacklist() {
        PUConfig.INSTANCE.battleBlacklist.set(new CopyOnWriteArrayList());
    }

    private static CopyOnWriteArrayList<Species> getBlacklist() {
        return PUConfig.INSTANCE.battleBlacklist.get().stream()
                .map(value -> {
                    try {
                        return Integer.parseInt(value.toString()); // Object를 String으로 변환 후 Integer로 변환
                    } catch (Exception e) {
                        return -1; // 변환 실패 시 -1 반환
                    }
                })
                .filter(integer -> integer > 0) // 유효한 값만 필터링
                .map(PixelmonSpecies::fromDex)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    private static void setBlackList(CopyOnWriteArrayList<Species> blackList) {
        List<? extends String> newBlacklist = (List)blackList.stream().map(Species::getDex).map(String::valueOf).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        PUConfig.INSTANCE.battleBlacklist.set(newBlacklist);
    }
} */

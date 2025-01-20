/*package net.sloth.command;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import net.sloth.command.argument.FormArgument;
import net.sloth.command.argument.PaletteArgument;
import net.sloth.command.argument.PokemonArgument;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PokemonLocatorCommand extends AbstractClientCommand {
    private static final Logger log = LogManager.getLogger(PokemonLocatorCommand.class);
    private static final Object $LOCK = new Object[0];
    private static Multimap<Species, String> searchedPokemonForms = Multimaps.synchronizedSetMultimap(HashMultimap.create());
    private static Multimap<Species, String> searchedPokemonPalettes = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    public PokemonLocatorCommand() {
        super("find");
        this.then(Commands.literal("add").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("pokemon", PokemonArgument.pokemon()).executes((context) -> {
            Species pokemon = PokemonArgument.getPokemon(context, "pokemon");
            searchedPokemonForms.put(pokemon, "All Forms");
            ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.added", new Object[]{pokemon.getLocalizedName()})).withStyle(TextFormatting.GRAY), false);
            return 1;
        })).then(Commands.literal("form").then(Commands.argument("form", FormArgument.pixelmonForm("pokemon")).executes((context) -> {
            Species pokemon = PokemonArgument.getPokemon(context, "pokemon");
            String form = FormArgument.getForm(context, "form");
            searchedPokemonForms.put(pokemon, form);
            ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.added.form", new Object[]{pokemon.getLocalizedName(), form})).withStyle(TextFormatting.GRAY), false);
            return 1;
        })))).then(Commands.literal("palette").then(Commands.argument("palette", PaletteArgument.pixelmonPalette("pokemon")).executes((context) -> {
            Species pokemon = PokemonArgument.getPokemon(context, "pokemon");
            String palette = PaletteArgument.getPalette(context, "palette");
            searchedPokemonPalettes.put(pokemon, palette);
            ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.added.palette", new Object[]{pokemon.getLocalizedName(), palette})).withStyle(TextFormatting.GRAY), false);
            return 1;
        })))));
        this.then(Commands.literal("clear").executes((context) -> {
            int amount = searchedPokemonForms.values().size();
            searchedPokemonForms.clear();
            amount += searchedPokemonPalettes.size();
            searchedPokemonPalettes.clear();
            ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.cleared", new Object[]{amount})).withStyle(TextFormatting.GRAY), false);
            return 1;
        }));
        this.then(Commands.literal("remove").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("pokemon", PokemonArgument.pokemon()).executes((context) -> {
            Species pokemon = PokemonArgument.getPokemon(context, "pokemon");
            if (searchedPokemonForms.remove(pokemon, "All Forms")) {
                ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.removed", new Object[]{pokemon.getLocalizedName()})).withStyle(TextFormatting.GRAY), false);
            } else {
                ((CommandSource)context.getSource()).sendFailure(new TranslationTextComponent("pixelmonutils.command.find.notfound"));
            }

            return 1;
        })).then(Commands.literal("form").then(Commands.argument("form", FormArgument.pixelmonForm("pokemon")).executes((context) -> {
            Species pokemon = PokemonArgument.getPokemon(context, "pokemon");
            String form = FormArgument.getForm(context, "form");
            if (searchedPokemonForms.remove(pokemon, form)) {
                ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.removed.form", new Object[]{pokemon.getLocalizedName(), form})).withStyle(TextFormatting.GRAY), false);
            } else {
                ((CommandSource)context.getSource()).sendFailure(new TranslationTextComponent("pixelmonutils.command.find.notfound"));
            }

            return 1;
        })))).then(Commands.literal("palette").then(Commands.argument("palette", PaletteArgument.pixelmonPalette("pokemon")).executes((context) -> {
            Species pokemon = PokemonArgument.getPokemon(context, "pokemon");
            String palette = PaletteArgument.getPalette(context, "palette");
            if (searchedPokemonPalettes.remove(pokemon, palette)) {
                ((CommandSource)context.getSource()).sendSuccess((new TranslationTextComponent("pixelmonutils.command.find.removed.palette", new Object[]{pokemon.getLocalizedName(), palette})).withStyle(TextFormatting.GRAY), false);
            } else {
                ((CommandSource)context.getSource()).sendFailure(new TranslationTextComponent("pixelmonutils.command.find.notfound"));
            }

            return 1;
        })))));
        this.then(Commands.literal("list").executes((context) -> {
            IFormattableTextComponent component = new TranslationTextComponent("pixelmonutils.command.find.list");

            for(Iterator var2 = getSearchedSpecies().iterator(); var2.hasNext(); component.append("\n")) {
                Species species = (Species)var2.next();
                component.append((new StringTextComponent(species.getLocalizedName())).withStyle(TextFormatting.DARK_GRAY)).append(": ");
                boolean leadingSeparator = false;
                if (searchedPokemonForms.containsKey(species)) {
                    component.append((new StringTextComponent(String.join(", ", searchedPokemonForms.get(species)))).withStyle(TextFormatting.GRAY));
                    leadingSeparator = true;
                }

                if (searchedPokemonPalettes.containsKey(species)) {
                    if (leadingSeparator) {
                        component.append((new StringTextComponent(", ")).withStyle(TextFormatting.GRAY));
                    }

                    component.append((new StringTextComponent(String.join(", ", searchedPokemonPalettes.get(species)))).withStyle(TextFormatting.GRAY));
                }
            }

            if (getSearchedSpecies().isEmpty()) {
                component.append((new TranslationTextComponent("pixelmonutils.command.find.list.empty")).withStyle(TextFormatting.GRAY));
            }

            ((CommandSource)context.getSource()).sendSuccess(component, false);
            return 1;
        }));
    }

    public static Set<Species> getSearchedSpecies() {
        Set<Species> species = new HashSet(searchedPokemonForms.keySet());
        species.addAll(searchedPokemonPalettes.keySet());
        return species;
    }

    public static Multimap<Species, String> getSearchedPokemonForms() {
        synchronized($LOCK) {
            return searchedPokemonForms;
        }
    }

    public static void setSearchedPokemonForms(Multimap<Species, String> searchedPokemonForms) {
        synchronized($LOCK) {
            PokemonLocatorCommand.searchedPokemonForms = searchedPokemonForms;
        }
    }

    public static Multimap<Species, String> getSearchedPokemonPalettes() {
        synchronized($LOCK) {
            return searchedPokemonPalettes;
        }
    }

    public static void setSearchedPokemonPalettes(Multimap<Species, String> searchedPokemonPalettes) {
        synchronized($LOCK) {
            PokemonLocatorCommand.searchedPokemonPalettes = searchedPokemonPalettes;
        }
    }
}*/
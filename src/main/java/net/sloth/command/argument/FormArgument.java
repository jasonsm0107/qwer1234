package net.sloth.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.pixelmonmod.pixelmon.api.command.PixelmonCommandUtils;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.species.Stats;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class FormArgument implements ArgumentType<String> {
    private static final List<String> excludedForms = Lists.newArrayList(new String[]{"Mega", "Gigantamax"});
    private final String pokemonArgumentName;
    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType((p_212594_0_) -> {
        return new TranslationTextComponent("argument.spec.invalid", new Object[]{p_212594_0_});
    });

    public String parse(StringReader reader) throws CommandSyntaxException {
        String form = reader.readUnquotedString();
        if (form.contains(" ")) {
            form = form.substring(0, form.indexOf(32));
        }

        int cursorPos = reader.getCursor();
        reader.setCursor(cursorPos);
        String finalForm = form;
        Optional<String> r = PixelmonSpecies.getAll().stream().flatMap((species) -> {
            return species.getForms().stream();
        }).distinct().map(Stats::getLocalizedName).filter((s) -> {
            return s.equalsIgnoreCase(finalForm);
        }).filter((s) -> {
            return !excludedForms.contains(s);
        }).findFirst();
        return (String)r.orElseThrow(() -> {
            return ERROR_INVALID_VALUE.create(finalForm);
        });
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Species species = PokemonArgument.getPokemon((CommandContext<CommandSource>) context, this.pokemonArgumentName);
        Set<String> suggestions = (Set)species.getForms().stream().map(Stats::getLocalizedName).filter((s) -> {
            return !excludedForms.contains(s);
        }).collect(Collectors.toSet());
        return context.getSource() instanceof ISuggestionProvider ? ISuggestionProvider.suggest(suggestions, builder) : Suggestions.empty();
    }

    public static FormArgument pixelmonForm(String pokemonArgumentName) {
        return new FormArgument(pokemonArgumentName);
    }

    public static <S> String getForm(CommandContext<S> pContext, String pName) {
        return (String)pContext.getArgument(pName, String.class);
    }

    public Collection<String> getExamples() {
        return PixelmonCommandUtils.SPEC_REQUIREMENTS;
    }

    public FormArgument(String pokemonArgumentName) {
        this.pokemonArgumentName = pokemonArgumentName;
    }

    public String getPokemonArgumentName() {
        return this.pokemonArgumentName;
    }
}

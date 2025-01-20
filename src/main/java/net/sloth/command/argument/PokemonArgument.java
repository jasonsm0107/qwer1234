package net.sloth.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.ITranslatable;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class PokemonArgument implements ArgumentType<Species> {
    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType((p_212594_0_) -> {
        return new TranslationTextComponent("argument.pokemon.invalid", new Object[]{p_212594_0_});
    });

    public PokemonArgument() {
    }

    public Species parse(StringReader reader) throws CommandSyntaxException {
        String pokemon = reader.readQuotedString();
        String finalPokemon = pokemon;
        return (Species)PixelmonSpecies.getAll().stream().filter((species) -> {
            return species.getLocalizedName().equals(finalPokemon);
        }).findFirst().orElseThrow(() -> {
            return ERROR_INVALID_VALUE.create(finalPokemon);
        });
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Stream<String> suggestions = PixelmonSpecies.getAll().stream().map(ITranslatable::getLocalizedName).map((name) -> {
            return String.format("\"%s\"", name);
        });
        return context.getSource() instanceof ISuggestionProvider ? ISuggestionProvider.suggest(suggestions, builder) : Suggestions.empty();
    }

    public static PokemonArgument pokemon() {
        return new PokemonArgument();
    }

    public static Species getPokemon(CommandContext<CommandSource> pContext, String pName) {
        return (Species)pContext.getArgument(pName, Species.class);
    }

    public Collection<String> getExamples() {
        return Lists.newArrayList(new String[]{(String)PixelmonSpecies.getAll().stream().map(ITranslatable::getLocalizedName).findFirst().get()});
    }
}

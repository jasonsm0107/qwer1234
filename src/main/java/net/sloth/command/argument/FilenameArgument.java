package net.sloth.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class FilenameArgument implements ArgumentType<File> {
    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType((p_212594_0_) -> {
        return new TranslationTextComponent("argument.file.invalid", new Object[]{p_212594_0_});
    });
    private final Path searchDir;

    public File parse(StringReader reader) throws CommandSyntaxException {
        String fileName = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        File[] files = this.searchDir.toFile().listFiles();
        if (files == null) {
            throw ERROR_INVALID_VALUE.create(fileName);
        } else {
            return (File)Arrays.stream(files).filter((file) -> {
                return file.getName().equals(fileName);
            }).findFirst().orElseThrow(() -> {
                return ERROR_INVALID_VALUE.create(fileName);
            });
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        List<String> suggestions = Arrays.stream(Optional.ofNullable(this.searchDir.toFile().listFiles()).orElse(new File[0])).map(File::getName).collect(Collectors.toList());
        return context.getSource() instanceof ISuggestionProvider ? ISuggestionProvider.suggest(suggestions, builder) : Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return ArgumentType.super.getExamples();
    }

    public static FilenameArgument filenameArgument(Path searchDir) {
        return new FilenameArgument(searchDir);
    }

    public static File getFile(CommandContext<CommandSource> pContext, String pName) {
        return (File)pContext.getArgument(pName, File.class);
    }

    public FilenameArgument(Path searchDir) {
        this.searchDir = searchDir;
    }
}

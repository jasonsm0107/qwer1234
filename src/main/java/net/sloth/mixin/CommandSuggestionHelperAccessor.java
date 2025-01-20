package net.sloth.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.minecraft.util.IReorderingProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({CommandSuggestionHelper.class})
public interface CommandSuggestionHelperAccessor {
    @Accessor
    TextFieldWidget getInput();

    @Accessor
    ParseResults<CommandSource> getCurrentParse();

    @Accessor
    void setCurrentParse(ParseResults<CommandSource> var1);

    @Invoker
    void callUpdateUsageInfo();

    @Accessor
    boolean getKeepSuggestions();

    @Accessor
    CommandSuggestionHelper.Suggestions getSuggestions();

    @Accessor
    void setSuggestions(CommandSuggestionHelper.Suggestions var1);

    @Accessor
    List<IReorderingProcessor> getCommandUsage();

    @Accessor
    boolean getOnlyShowIfCursorPastError();

    @Accessor
    CompletableFuture<Suggestions> getPendingSuggestions();

    @Accessor
    void setPendingSuggestions(CompletableFuture<Suggestions> var1);
}

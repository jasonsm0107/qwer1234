package net.sloth.command;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.sloth.mixin.CommandSuggestionHelperAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;

public class ClientCommandSuggestionHelper extends CommandSuggestionHelper {
    public static final char marker = '#';
    private static final CommandDispatcher<CommandSource> commandDispatcher = new CommandDispatcher();

    public ClientCommandSuggestionHelper(Minecraft mc, Screen screen, TextFieldWidget inputField, FontRenderer font, boolean commandsOnly, boolean hasCursor, int minAmountRendered, int maxAmountRendered, boolean isChat, int color) {
        super(mc, screen, inputField, font, commandsOnly, hasCursor, minAmountRendered, maxAmountRendered, isChat, color);
    }

    public void updateCommandInfo() {
        String s = this.accessor().getInput().getValue();
        if (s.startsWith("#")) {
            if (this.accessor().getCurrentParse() != null && !this.accessor().getCurrentParse().getReader().getString().equals(s)) {
                this.accessor().setCurrentParse((ParseResults)null);
            }

            if (!this.accessor().getKeepSuggestions()) {
                this.accessor().getInput().setSuggestion((String)null);
                this.accessor().setSuggestions((CommandSuggestionHelper.Suggestions)null);
            }

            this.accessor().getCommandUsage().clear();
            StringReader stringreader = new StringReader(s);
            boolean flag = stringreader.canRead() && stringreader.peek() == '#';
            if (flag) {
                stringreader.skip();
            }

            int i = this.accessor().getInput().getCursorPosition();
            if (flag) {
                CommandDispatcher<CommandSource> commandsDispatcher = commandDispatcher;
                if (this.accessor().getCurrentParse() == null) {
                    if (Minecraft.getInstance().player == null) {
                        return;
                    }

                    this.accessor().setCurrentParse(commandsDispatcher.parse(stringreader, Minecraft.getInstance().player.createCommandSourceStack()));
                }

                int j = this.accessor().getOnlyShowIfCursorPastError() ? stringreader.getCursor() : 1;
                if (i >= j && (this.accessor().getSuggestions() == null || !this.accessor().getKeepSuggestions())) {
                    this.accessor().setPendingSuggestions(commandsDispatcher.getCompletionSuggestions(this.accessor().getCurrentParse(), i));
                    this.accessor().getPendingSuggestions().thenRun(() -> {
                        if (this.accessor().getPendingSuggestions().isDone()) {
                            this.accessor().callUpdateUsageInfo();
                        }

                    });
                }
            }
        } else {
            super.updateCommandInfo();
        }

    }

    private CommandSuggestionHelperAccessor accessor() {
        return (CommandSuggestionHelperAccessor)this;
    }

    public static void registerCommand(LiteralArgumentBuilder<CommandSource> builder) {
        commandDispatcher.register(builder);
    }

    public static CommandDispatcher<CommandSource> getCommandDispatcher() {
        return commandDispatcher;
    }
}

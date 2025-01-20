/*package net.sloth.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.sloth.command.argument.PokemonArgument;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;

public class PUCommands {
    public PUCommands() {
    }

    public static void register() {
        ArgumentTypes.register("pokemon", PokemonArgument.class, new ArgumentSerializer(PokemonArgument::pokemon));
        register(HomeCommand::new);
        register(ResetPokemonCommand::new);
        register(CatchSeriesHelperCommand::new);
        register(FishingCommand::new);
        register(BattleCommand::new);
        register(LootFilterCommand::new);
        register(TimedCommandsCommand::new);
        register(PokemonLocatorCommand::new);
    }

    private static void register(Supplier<LiteralArgumentBuilder<CommandSource>> command) {
        ClientCommandSuggestionHelper.registerCommand((LiteralArgumentBuilder)command.get());
    }
}*/

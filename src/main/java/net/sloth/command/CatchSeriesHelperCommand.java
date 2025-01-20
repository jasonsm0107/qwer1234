/*package net.sloth.command;


import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import net.sloth.command.argument.PokemonArgument;
import net.sloth.util.PUTranslation;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CatchSeriesHelperCommand extends AbstractClientCommand {
    private static final Logger log = LogManager.getLogger(CatchSeriesHelperCommand.class);
    private static final Object $LOCK = new Object[0];
    private static Species target = null;

    public CatchSeriesHelperCommand() {
        super("catchSeries");
        this.then(Commands.literal("start").then(Commands.argument("target", PokemonArgument.pokemon()).executes((context) -> {
            Species species = PokemonArgument.getPokemon(context, "target");
            setTarget(species);
            ((CommandSource)context.getSource()).sendSuccess(PUTranslation.of("command.catchseries.set", new Object[]{species.getLocalizedName()}), false);
            return 1;
        })));
        this.then(Commands.literal("stop").executes((context) -> {
            if (getTarget() != null) {
                ((CommandSource)context.getSource()).sendSuccess(PUTranslation.of("command.catchseries.stop", new Object[]{getTarget().getLocalizedName()}), false);
                setTarget((Species)null);
            }

            return 1;
        }));
    }

    public static Species getTarget() {
        synchronized($LOCK) {
            return target;
        }
    }

    public static void setTarget(Species target) {
        synchronized($LOCK) {
            CatchSeriesHelperCommand.target = target;
        }
    }
}*/

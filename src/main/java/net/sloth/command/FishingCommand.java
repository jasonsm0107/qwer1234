/*package net.sloth.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.sloth.util.PUChatUtil;
import net.sloth.util.PUTranslation;
import net.minecraft.command.Commands;

public class FishingCommand extends AbstractClientCommand {
    private static final Object $LOCK = new Object[0];
    private static int minimalMarks = 1;

    public FishingCommand() {
        super("fishing");
        this.then(Commands.literal("minimalMarks").then(Commands.argument("amount", IntegerArgumentType.integer(1, 10)).executes((context) -> {
            int amount = IntegerArgumentType.getInteger(context, "amount");
            setMinimalMarks(amount);
            PUChatUtil.chat(PUTranslation.of("fishing.minimal.changed", new Object[]{amount}));
            return 1;
        })));
    }

    public static int getMinimalMarks() {
        synchronized($LOCK) {
            return minimalMarks;
        }
    }

    private static void setMinimalMarks(int minimalMarks) {
        synchronized($LOCK) {
            FishingCommand.minimalMarks = minimalMarks;
        }
    }
}*/
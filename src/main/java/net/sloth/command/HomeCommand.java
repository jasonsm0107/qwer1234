/*package net.sloth.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;

public class HomeCommand extends LiteralArgumentBuilder<CommandSource> {
    public HomeCommand() {
        super("home");
        this.executes((context) -> {
            if (Minecraft.getInstance().player == null) {
                return 1;
            } else {
                Minecraft.getInstance().player.chat("/home home");
                return 1;
            }
        });
    }
}*/

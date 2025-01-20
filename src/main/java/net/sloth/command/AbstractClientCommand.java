/*package net.sloth.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;

public class AbstractClientCommand extends LiteralArgumentBuilder<CommandSource> {
    private final Object $lock = new Object[0];
    private final Minecraft minecraft = Minecraft.getInstance();

    public AbstractClientCommand(String literal) {
        super(literal);
    }

    protected static int success() {
        return 1;
    }

    protected Optional<ClientPlayerEntity> player() {
        synchronized(this.$lock) {
            return Optional.ofNullable(this.minecraft.player);
        }
    }

    protected void chat(String message) {
        Minecraft.getInstance().execute(() -> {
            this.player().ifPresent((player) -> {
                player.chat(message);
            });
        });
    }

    protected Minecraft getMinecraft() {
        return this.minecraft;
    }
}*/

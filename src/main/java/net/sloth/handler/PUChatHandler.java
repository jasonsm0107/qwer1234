/*package net.sloth.handler;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.sloth.command.ClientCommandSource;
import net.sloth.command.ClientCommandSuggestionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(
        modid = "ingka1111",
        bus = Bus.FORGE,
        value = {Dist.CLIENT}
)
public class PUChatHandler {
    private static final Logger log = LogManager.getLogger(PUChatHandler.class);

    public PUChatHandler() {
    }

    @SubscribeEvent
    public static void onPlayerChat(ClientChatEvent e) {
        if (e.getMessage().startsWith("#")) {
            try {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                ClientCommandSource commandSource = new ClientCommandSource(player, player.position(), player.getRotationVector(), (ServerWorld)null, 4, player.getName().getString(), player.getDisplayName(), (MinecraftServer)null, player);
                ParseResults<CommandSource> parse = ClientCommandSuggestionHelper.getCommandDispatcher().parse(e.getMessage().substring(1), commandSource);
                if (parse.getContext().getNodes().size() > 0) {
                    e.setCanceled(true);
                    Minecraft.getInstance().gui.getChat().addRecentChat(e.getMessage());
                    ClientCommandSuggestionHelper.getCommandDispatcher().execute(parse);
                }
            } catch (CommandSyntaxException var4) {
                CommandSyntaxException ex = var4;
                log.trace(ex);
            }
        }

    }
}*/



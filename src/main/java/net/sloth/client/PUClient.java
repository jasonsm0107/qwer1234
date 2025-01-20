package net.sloth.client;

import com.pixelmonmod.pixelmon.api.pokedex.PokedexRegistrationStatus;
import com.pixelmonmod.pixelmon.client.storage.ClientStorageManager;
//import net.sloth.command.PUCommands;
import net.sloth.feature.LootBallFinderMode;
import net.sloth.handler.PUTaskHandler;
import net.sloth.keybinding.PUKeybindings;
import net.sloth.storage.StorageManager;
import net.sloth.util.PUSettings;
import net.minecraft.block.BlockState;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PUClient {
    public PUClient() {
    }

    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(PUClient::clientSetup);
        forgeEventBus.addListener(PUClient::onClientJoinWorld);
        forgeEventBus.addListener(PUClient::onClientLeaveWorld);
        forgeEventBus.addListener(PUClient::onClickOnBlock);
    }

    private static void clientSetup(FMLClientSetupEvent e) {
        PUKeybindings.init();
        //PUCommands.register();
        PUTaskHandler.init();
    }

    private static void onClientJoinWorld(ClientPlayerNetworkEvent.LoggedInEvent e) {
        StorageManager.init();
    }

    private static void onClientLeaveWorld(ClientPlayerNetworkEvent.LoggedOutEvent e) {
        StorageManager.save();
        PUSettings.reset();
    }

    private static void onClickOnBlock(PlayerInteractEvent.RightClickBlock e) {
        if (e.getSide() == LogicalSide.CLIENT) {
            BlockState state = e.getWorld().getBlockState(e.getPos());
            if (LootBallFinderMode.ALL.getValidBlockTest().test(state)) {
                StorageManager.getStorage().addKnownLootBall(e.getPos(), e.getWorld().dimension().location());
            }

            ClientStorageManager.pokedex.getSeenMap().entrySet().stream().filter((entry) -> {
                return !((PokedexRegistrationStatus)entry.getValue()).equals(PokedexRegistrationStatus.SEEN);
            });
        }
    }
}

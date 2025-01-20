package net.sloth.handler;

import net.sloth.keybinding.PUKeybindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "ingka1111",
        bus = Bus.FORGE,
        value = {Dist.CLIENT}
)
public class PUKeybindingHandler {
    public PUKeybindingHandler() {
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        PUKeybindings.checkKeyBindings();
    }
}

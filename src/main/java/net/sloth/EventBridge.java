package net.sloth;

import net.sloth.NativeBridge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.sloth.keybinding.ModKeyBindings;

public class EventBridge {
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // 키바인딩 상태 체크
            ModKeyBindings.onTick();
            // 기존 이벤트 처리
            NativeBridge.handleForgeEvent(event);
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide()) {
            NativeBridge.handleForgeEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        NativeBridge.handleForgeEvent(event);
    }
}
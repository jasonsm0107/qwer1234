package net.sloth.mixin;

import com.pixelmonmod.pixelmon.client.gui.ItemDropsScreen;
import com.pixelmonmod.pixelmon.comm.packetHandlers.itemDrops.ItemDropPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
        value = {ItemDropsScreen.class},
        remap = false
)
public interface ItemDropScreenAccessor {
    @Accessor
    ItemDropPacket getDrops();
}

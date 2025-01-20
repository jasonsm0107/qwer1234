package net.sloth.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "ingka1111",
        bus = Bus.MOD,
        value = {Dist.CLIENT}
)
public class PUSounds {
    public static final SoundEvent SHINY_FOUND = register("shiny_found", "shiny_found");
    public static final SoundEvent LEGENDARY_FOUND = register("legendary_found", "legendary_found");
    public static final SoundEvent NEW_POKEMON_FOUND = register("new_found", "new_pokemon_found");

    public PUSounds() {
    }

    private static SoundEvent register(String name, String path) {
        SoundEvent event = new SoundEvent(new ResourceLocation("pixelmonutils", path));
        event.setRegistryName(new ResourceLocation("", name));
        return event;
    }

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().register(SHINY_FOUND);
        e.getRegistry().register(LEGENDARY_FOUND);
        e.getRegistry().register(NEW_POKEMON_FOUND);
    }
}

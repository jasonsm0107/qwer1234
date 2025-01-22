package net.sloth.keybinding;

import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.sloth.NativeBridge;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class ModKeyBindings {
    private static final List<ModKeyBinding> ALL_BINDINGS = new ArrayList<>();

    public static final ModKeyBinding TOGGLE_FEATURE = register(
            new ModKeyBinding("key.ingka1111.toggle_feature",
                    InputMappings.Type.KEYSYM,
                    GLFW.GLFW_KEY_K,
                    "key.categories.ingka1111",
                    () -> NativeBridge.handleKeyPress(GLFW.GLFW_KEY_K))
    );

    public static final ModKeyBinding OPEN_GUI = register(
            new ModKeyBinding("key.ingka1111.open_gui",
                    InputMappings.Type.KEYSYM,
                    GLFW.GLFW_KEY_G,
                    "key.categories.ingka1111",
                    () -> NativeBridge.handleKeyPress(GLFW.GLFW_KEY_G))
    );

    public static void register() {
        for (ModKeyBinding binding : ALL_BINDINGS) {
            ClientRegistry.registerKeyBinding(binding.getKeyBinding());
        }
    }

    public static void onTick() {
        for (ModKeyBinding binding : ALL_BINDINGS) {
            binding.onTick();
        }
    }

    private static ModKeyBinding register(ModKeyBinding binding) {
        ALL_BINDINGS.add(binding);
        return binding;
    }
}
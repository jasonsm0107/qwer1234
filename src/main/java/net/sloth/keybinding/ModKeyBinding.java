package net.sloth.keybinding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.sloth.NativeBridge;

public class ModKeyBinding {
    private final KeyBinding keyBinding;
    private final Runnable action;
    private boolean wasPressed;

    public ModKeyBinding(String name, InputMappings.Type type, int keyCode, String category, Runnable action) {
        this.keyBinding = new KeyBinding(name, type, keyCode, category);
        this.action = action;
        this.wasPressed = false;
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public void onTick() {
        boolean isPressed = keyBinding.isDown();
        if (isPressed != wasPressed) {
            wasPressed = isPressed;
            if (isPressed) {
                NativeBridge.handleKeyPress(keyBinding.getKey().getValue());
                if (action != null) {
                    action.run();
                }
            } else {
                NativeBridge.handleKeyRelease(keyBinding.getKey().getValue());
            }
        }
    }
}
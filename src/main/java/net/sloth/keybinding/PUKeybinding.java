package net.sloth.keybinding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.util.InputMappings.Type;
import net.minecraftforge.client.settings.KeyConflictContext;

public class PUKeybinding extends KeyBinding {
    private final KeyBindingAction action;

    public PUKeybinding(String langKey, int defaultKey, KeybindingCategory category, KeyBindingAction action) {
        super(String.format("%s.%s", "Pixel Radar", langKey), KeyConflictContext.UNIVERSAL, Type.KEYSYM, defaultKey, category.getCategoryString());
        this.action = action;
    }

    public PUKeybinding(String langKey, KeybindingCategory category, KeyBindingAction action) {
        this(langKey, InputMappings.UNKNOWN.getValue(), category, action);
    }

    public KeyBindingAction getAction() {
        return this.action;
    }

    @FunctionalInterface
    public interface KeyBindingAction {
        void onPress();
    }
}
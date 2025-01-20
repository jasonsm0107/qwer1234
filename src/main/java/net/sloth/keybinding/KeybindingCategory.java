package net.sloth.keybinding;

public class KeybindingCategory {
    private final String id;

    public String getCategoryString() {
        return String.format("%s.category.%s", "Pixel Radar", this.id);
    }

    private KeybindingCategory(String id) {
        this.id = id;
    }

    public static KeybindingCategory of(String id) {
        return new KeybindingCategory(id);
    }
}

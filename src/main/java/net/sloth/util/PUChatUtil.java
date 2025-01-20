package net.sloth.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class PUChatUtil {
    public PUChatUtil() {
    }

    public static void chat(ITextComponent textComponent) {
        Minecraft.getInstance().gui.handleChat(ChatType.CHAT, textComponent, Util.NIL_UUID);
    }
}

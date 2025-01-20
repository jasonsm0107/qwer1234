package net.sloth.mixin;

//import net.sloth.command.ClientCommandSuggestionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.sloth.command.ClientCommandSuggestionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ChatScreen.class})
public class MixinChatScreen {
    public MixinChatScreen() {
    }

    @Redirect(
            method = {"init"},
            at = @At(
                    value = "NEW",
                    target = "Lnet/minecraft/client/gui/CommandSuggestionHelper;"
            )
    )
    public CommandSuggestionHelper init(Minecraft pMinecraft, Screen pScreen, TextFieldWidget pInput, FontRenderer pFont, boolean pCommandsOnly, boolean pOnlyShowIfCursorPastError, int pLineStartOffset, int pSuggestionLineLimit, boolean pAnchorToBottom, int pFillColor) {
        return new ClientCommandSuggestionHelper(pMinecraft, pScreen, pInput, pFont, pCommandsOnly, pOnlyShowIfCursorPastError, pLineStartOffset, pSuggestionLineLimit, pAnchorToBottom, pFillColor);
    }
}

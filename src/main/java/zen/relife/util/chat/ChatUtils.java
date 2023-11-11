package zen.relife.util.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;

public class ChatUtils {
    public static void component(TextComponentTranslation component) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().ingameGUI.getChatGUI() == null) {
            return;
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("").appendSibling(component));
    }

    public static void message(Object message) {
        ChatUtils.component(new TextComponentTranslation(ChatFormatting.LIGHT_PURPLE + "[Relife]\u00a77" + message));
    }

    public static void report(String message) {
        ChatUtils.message(ChatFormatting.GREEN + message);
    }

    public static void warning(Object message) {
        ChatUtils.message("\u00a78[\u00a7eWARNING\u00a78]\u00a7e " + message);
    }

    public static void error(Object message) {
        ChatUtils.message("\u00a78[\u00a74ERROR\u00a78]\u00a7c " + message);
    }
}


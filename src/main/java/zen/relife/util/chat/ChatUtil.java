package zen.relife.util.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil {
    public static String color(String s) {
        return s.replaceAll("&", "§");
    }

    public static void sendRawMessage(String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString(message)
        );
    }

    public static void sendCMessage(String message) {
        sendRawMessage(color(message));
    }

    public static void sendMeeage(String message) {
        sendRawMessage("&8[&9Relife&8]&r" + message);
    }
}

package zen.relife.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import zen.relife.Relife;

public class Helper {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String message) {
        message = "[" + Relife.NAME + "] " + message;
        new ChatUtil.ChatMessageBuilder(true, true).appendText(message).setColor(TextFormatting.LIGHT_PURPLE).build().displayClientSided();
    }

    public static void Get_information(String string) {

        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(string));

    }

    public static boolean onServer(String server) {
        return !mc.isSingleplayer() && Helper.mc.getCurrentServerData().serverIP.toLowerCase().contains(server);
    }

    public static void sendMessageWithoutPrefix(String string) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(string));
    }

}

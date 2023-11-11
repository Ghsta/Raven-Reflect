package zen.relife.util;

import net.minecraft.client.Minecraft;

public class FontUtil {
    public static int getStringWidth(String text) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    public static int getFontHeight() {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }

    public static void drawString(String text, double x, double y, int color) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, (int) x, (int) y, color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public static void drawCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double) (Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double) (Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawTotalCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double) (Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2), y - (double) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2), color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double) (Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2), y - (double) ((float) Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2.0f), color);
    }
}


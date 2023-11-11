package zen.relife.utils;

import zen.relife.manager.impl.FontManager;

public class TextUtil {

    public static void drawCenteredString(String text, float x, float y, int color, boolean shadow) {
        FontManager.F18.drawString(text, x - FontManager.F18.getStringWidth(text) / 2f, y, color, shadow);
    }
}

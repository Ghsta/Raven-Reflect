package zen.relife.util;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ColorHelper {

    /**
     * Sets the color using an int color value using GlStateManager
     *
     * @param color The given int color value
     */

    public static void color(int color) {
        float alpha = (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;
        GlStateManager.color(red, green, blue, alpha);
    }

    /**
     * Returns a Color from an int color value
     *
     * @param color The given int color value
     * @return The Color value
     */

    public static Color intColorToRGB(int color) {
        float alpha = (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;
        return new Color(red, green, blue, alpha);
    }

    /**
     * Returns a Color from an X and Y position on the screen
     *
     * @param x The X Coordinate
     * @param y The Y Coordinate
     * @return The Color
     */

}
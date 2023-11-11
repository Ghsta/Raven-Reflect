package zen.relife.render.font;

import net.minecraft.client.Minecraft;

import java.awt.*;

public class Render2DUtils {
    private static final Minecraft mc;

    static {
        mc = Minecraft.getMinecraft();
    }


    public static int reAlpha(final int color, final float alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }
}
    

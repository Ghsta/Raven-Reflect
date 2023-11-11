package zen.relife.util;

import net.minecraft.client.gui.ScaledResolution;
import zen.relife.Relife;

public class ResolutionHelper {

    private static ScaledResolution scaledResolution;

    public static int getHeight() {
        scaledResolution = new ScaledResolution(Relife.INSTANCE.mc);
        return scaledResolution.getScaledHeight();
    }

    public static int getWidth() {
        scaledResolution = new ScaledResolution(Relife.INSTANCE.mc);
        return scaledResolution.getScaledWidth();
    }

    public static int getFactor() {
        scaledResolution = new ScaledResolution(Relife.INSTANCE.mc);
        return scaledResolution.getScaleFactor();
    }
}
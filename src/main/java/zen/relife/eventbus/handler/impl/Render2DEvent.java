package zen.relife.eventbus.handler.impl;

import net.minecraft.client.gui.ScaledResolution;
import zen.relife.eventbus.Event;

public final class Render2DEvent implements Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    public Render2DEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

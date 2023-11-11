package zen.relife.module.impl.render;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zen.relife.module.Category;
import zen.relife.module.Module;

import java.awt.*;

public class Chams
        extends Module {
    public Chams() {
        super("Chams", 0, Category.RENDER, false);
    }

    public static Color rainbow(int n) {
        return Color.getHSBColor((float) (Math.ceil((double) (System.currentTimeMillis() + (long) n) / 20.0) % 360.0 / 360.0), 0.8f, 1.0f).brighter();
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre e) {
        if (e.getEntity() != Chams.mc.player) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Post e) {
        if (e.getEntity() != Chams.mc.player) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
        }
    }

    public void glColor(float n, int n2, int n3, int n4) {
        GL11.glColor4f(0.003921569f * (float) n2, 0.003921569f * (float) n3, 0.003921569f * (float) n4, n);
    }
}


package zen.relife.hud.mod;

import net.minecraft.client.Minecraft;
import zen.relife.hud.DraggableComponent;

import java.awt.*;

public class HudMod {

    public static Minecraft mc = Minecraft.getMinecraft();

    public String name;
    public boolean enabled;
    public DraggableComponent drag;

    public int x, y;

    public HudMod(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;

        drag = new DraggableComponent(x, y, x + getWidth(), y + getHeight(), new Color(0, 0, 0, 0).getRGB());
    }

    public int getWidth() {
        return 50;
    }

    public int getHeight() {
        return 50;
    }

    public void draw() {

    }

    public void renderDummy(int mouseX, int mouseY) {
        drag.draw(mouseX, mouseY);
    }

    public int getX() {
        return drag.getxPosition();
    }

    public int getY() {
        return drag.getyPosition();
    }
}

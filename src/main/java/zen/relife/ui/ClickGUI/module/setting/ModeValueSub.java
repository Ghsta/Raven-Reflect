package zen.relife.ui.ClickGUI.module.setting;


import org.lwjgl.opengl.GL11;
import zen.relife.Relife;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Module;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.setting.ModeSetting;
import zen.relife.setting.Setting;
import zen.relife.util.ClickGUIUtils;
import zen.relife.util.FontUtil;
import zen.relife.util.Helper2D;

import java.awt.*;

public class ModeValueSub {
    private final Setting setting;
    public String modes;
    private boolean hovered = false;

    public ModeValueSub(String modes, Setting setting) {
        this.modes = modes;
        this.setting = setting;
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height, int tot) {
        this.reload(mouseX, mouseY, particalTicks, x + width, y, width, height, tot);
    }

    public void reload(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height, int tot) {
        if (ClickGui.Element.getCurrent() != ClickGui.Element.getMax()) {
            if (tot <= (int) ClickGui.Element.getCurrent() * 16 && tot >= 0) {
                this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
            }
        } else {
            this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        }
        int color = new Color(47, 47, 47, 233).getRGB();
        if (this.hovered) {
            color = new Color(56, 56, 56, 255).getRGB();
        }
        Helper2D.drawRoundedRectangle(x, y, width, height, 0, color, true, -1);
        Helper2D.drawRoundedRectangle(x, y, 2, height, 0, color, true, -1);
        if (((ModeSetting) this.setting).getCurrent().equalsIgnoreCase(this.modes)) {
            if (((ModeSetting) this.setting).getCurrent() == "" || ((ModeSetting) this.setting).getCurrent() != this.modes) {
                ((ModeSetting) this.setting).setCurrent(this.modes);
            }
            if (ClickGui.useFont.getEnable()) {
                FontManager.F16.drawCenteredString(this.modes, x + width / 2, y + height / 2 - 3, new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 255).getRGB());
            } else {
                GL11.glPushMatrix();
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                FontUtil.drawCenteredString(this.modes, (float) (x + width / 2) * 1.3333334f, (float) (y + height / 2 - 3) * 1.3333334f, new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 255).getRGB());
                GL11.glPopMatrix();
            }
        } else if (ClickGui.useFont.getEnable()) {
            FontManager.F16.drawCenteredString(this.modes, x + width / 2, y + height / 2 - 3, Color.WHITE.getRGB());
        } else {
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.75f);
            FontUtil.drawCenteredString(this.modes, (float) (x + width / 2) * 1.3333334f, (float) (y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
            GL11.glPopMatrix();
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered && mouseButton == 0) {
            ((ModeSetting) this.setting).setCurrent(this.modes);
            for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
                m.setMode(this.setting);
            }
        }
    }
}


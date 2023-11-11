package zen.relife.ui.ClickGUI.module.setting;


import org.lwjgl.opengl.GL11;
import zen.relife.Relife;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Module;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.Setting;
import zen.relife.util.ClickGUIUtils;
import zen.relife.util.FontUtil;
import zen.relife.util.Helper2D;
import zen.relife.util.RenderUtil;

import java.awt.*;

public class BooleanValuePart
        extends SettingPanel {
    private boolean hovered = false;

    public BooleanValuePart(Setting setting) {
        super(setting);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height, int tot) {
        if (ClickGui.Element.getCurrent() != ClickGui.Element.getMax()) {
            if (tot <= (int) ClickGui.Element.getCurrent() * 16 && tot >= 0) {
                this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
            }
        } else {
            this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        }
        int color = new Color(55, 55, 55, 255).getRGB();
        Helper2D.drawRoundedRectangle(x, y, width, height, 0, new Color(104, 150, 248, 207).getRGB(), true, 1);
        Helper2D.drawRoundedRectangle(x + 1, y + 2, height - 6, height - 6, 5, new Color(104, 150, 248, 179).getRGB(), true, 0);
        if (((EnableSetting) this.setting).getEnable()) {
            RenderUtil.drawCheckmark(x + 2, y + 6, new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 255).getRGB());
        }
        if (((EnableSetting) this.setting).getEnable()) {
            if (ClickGui.useFont.getEnable()) {
                FontManager.F16.drawString(this.setting.getName(), x + 14, y + height / 2 - 3, new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 255).getRGB());
            } else {
                GL11.glPushMatrix();
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                FontUtil.drawString(this.setting.getName(), (float) (x + 14) * 1.3333334f, (float) (y + height / 2 - 3) * 1.3333334f, new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 255).getRGB());
                GL11.glPopMatrix();
            }
        } else if (ClickGui.useFont.getEnable()) {
            FontManager.F16.drawString(this.setting.getName(), x + 14, y + height / 2 - 3, Color.WHITE.getRGB());
        } else {
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.75f);
            FontUtil.drawString(this.setting.getName(), (float) (x + 14) * 1.3333334f, (float) (y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
            GL11.glPopMatrix();
        }
        super.drawScreen(mouseX, mouseY, particalTicks, x, y, width, height, tot);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered && mouseButton == 0) {
            ((EnableSetting) this.setting).setEnable(!((EnableSetting) this.setting).getEnable());
            for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                module.onCheck(this.setting);
            }
        }
    }
}


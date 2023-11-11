package zen.relife.ui.ClickGUI.module.setting;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import zen.relife.Relife;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Module;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.setting.IntegerSetting;
import zen.relife.setting.Setting;
import zen.relife.util.ClickGUIUtils;
import zen.relife.util.FontUtil;
import zen.relife.util.Helper2D;

import java.awt.*;
import java.text.DecimalFormat;

public class NumberValuePart
        extends SettingPanel {
    private final boolean silderHovered = false;
    private final double offsetX = 0.0;
    private boolean hovered = false;
    private boolean dragging = false;

    public NumberValuePart(Setting setting) {
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
        if (this.hovered) {
            color = new Color(55, 55, 55, 255).getRGB();
        }
        Helper2D.drawRoundedRectangle(x, y, width, height, 0, color, true, 0);
        IntegerSetting setting1 = (IntegerSetting) this.setting;
        double percentBar = (setting1.getCurrent() - setting1.getMin()) / (setting1.getMax() - setting1.getMin());
        if (this.dragging) {
            for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
                m.isSilder(this.setting);
            }
            double value = setting1.getMax() - setting1.getMin();
            double val = setting1.getMin() + MathHelper.clamp((double) (mouseX - x) / (double) width, 0.0, 1.0) * value;
            Double tmp = 1.0;
            if (((IntegerSetting) this.setting).getDou() == 2) {
                Double retn;
                DecimalFormat df = new DecimalFormat("#.00");
                String str = df.format(val);
                tmp = retn = new Double(str);
            }
            if (((IntegerSetting) this.setting).getDou() == 1) {
                Double retn;
                DecimalFormat df = new DecimalFormat("#.0");
                String str = df.format(val);
                tmp = retn = new Double(str);
            } else if (((IntegerSetting) this.setting).getDou() == 0) {
                Double retn;
                DecimalFormat df = new DecimalFormat("#");
                String str = df.format(val);
                tmp = retn = new Double(str);

            }
            setting1.setCurrent(tmp);
        }
        Gui.drawRect(x, y + 9, x + (int) (percentBar * (double) width), y + 15, new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 190).getRGB());
        Gui.drawRect(x + (int) (percentBar * (double) width) - 1, y + 9, x + (int) Math.min(percentBar * (double) width, width), y + 15, new Color(0, 0, 0, 100).getRGB());
        GL11.glPushMatrix();
        if (ClickGui.useFont.getEnable()) {
            FontManager.F14.drawString(setting1.getName(), x + 3, y + height / 3 - 2, Color.WHITE.getRGB());
            if (this.setting.getName().equalsIgnoreCase("MaxElement")) {
                if (ClickGui.Element.getCurrent() == ClickGui.Element.getMax()) {
                    FontManager.F14.drawString("NONE", x + width - FontManager.F16.getStringWidth("" + ((IntegerSetting) this.setting).getCurrent()) - 2, y + height / 3 - 2, Color.WHITE.getRGB());
                } else {
                    FontManager.F14.drawString("" + ((IntegerSetting) this.setting).getCurrent(), x + width - FontManager.F16.getStringWidth("" + ((IntegerSetting) this.setting).getCurrent()) - 2, y + height / 3 - 2, Color.WHITE.getRGB());
                }
            } else {
                FontManager.F14.drawString("" + ((IntegerSetting) this.setting).getCurrent(), x + width - FontManager.F16.getStringWidth("" + ((IntegerSetting) this.setting).getCurrent()) - 2, y + height / 3 - 2, Color.WHITE.getRGB());
            }
        } else {
            GL11.glScalef(0.75f, 0.75f, 0.75f);
            FontUtil.drawString(setting1.getName(), (float) (x + 4) * 1.3333334f, (float) (y + height / 3 - 3) * 1.3333334f, Color.WHITE.getRGB());
            if (this.setting.getName().equalsIgnoreCase("MaxElement")) {
                if (ClickGui.Element.getCurrent() == ClickGui.Element.getMax()) {
                    FontUtil.drawString("NONE", (float) (x + width - FontUtil.getStringWidth("" + ((IntegerSetting) this.setting).getCurrent())) * 1.3333334f, (float) (y + height / 3 - 3) * 1.3333334f, Color.WHITE.getRGB());
                } else {
                    FontUtil.drawString("" + ((IntegerSetting) this.setting).getCurrent(), (float) (x + width - FontUtil.getStringWidth("" + ((IntegerSetting) this.setting).getCurrent())) * 1.3333334f, (float) (y + height / 3 - 3) * 1.3333334f, Color.WHITE.getRGB());
                }
            } else {
                FontUtil.drawString("" + ((IntegerSetting) this.setting).getCurrent(), (float) (x + width - FontUtil.getStringWidth("" + ((IntegerSetting) this.setting).getCurrent())) * 1.3333334f, (float) (y + height / 3 - 3) * 1.3333334f, Color.WHITE.getRGB());
            }
        }
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, particalTicks, x, y, width, height, tot);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered && mouseButton == 0) {
            this.dragging = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }
}


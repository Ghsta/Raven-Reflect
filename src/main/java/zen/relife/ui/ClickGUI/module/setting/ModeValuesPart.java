package zen.relife.ui.ClickGUI.module.setting;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.setting.ModeSetting;
import zen.relife.setting.Setting;
import zen.relife.util.ClickGUIUtils;
import zen.relife.util.FontUtil;
import zen.relife.util.Helper2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModeValuesPart
        extends SettingPanel {
    public final List<ModeValueSub> modesPanelList = new ArrayList<ModeValueSub>();
    private boolean hovered = false;
    private boolean hovered1 = false;

    public ModeValuesPart(Setting setting) {
        super(setting);
        ModeSetting settingw = (ModeSetting) this.setting;
        if (settingw.getModes().size() > 0) {
            this.modeY += settingw.getModes().size() * 16;
            this.modes = settingw.getModes().size();
        }
        for (int i = 0; i < settingw.getModes().size(); ++i) {
            this.modesPanelList.add(new ModeValueSub(settingw.getModes().get(i), setting));
        }
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        double var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        float var11 = (float) (color >> 24 & 0xFF) / 255.0f;
        float var6 = (float) (color >> 16 & 0xFF) / 255.0f;
        float var7 = (float) (color >> 8 & 0xFF) / 255.0f;
        float var8 = (float) (color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(left, bottom, 0.0).endVertex();
        worldRenderer.pos(right, bottom, 0.0).endVertex();
        worldRenderer.pos(right, top, 0.0).endVertex();
        worldRenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        ModeValuesPart.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ModeValuesPart.rectangle(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ModeValuesPart.rectangle(x, y, x + width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ModeValuesPart.rectangle(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ModeValuesPart.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public List<ModeValueSub> getModesPanelList() {
        return this.modesPanelList;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height, int tot) {
        if (ClickGui.Element.getCurrent() != ClickGui.Element.getMax()) {
            if (tot <= (int) ClickGui.Element.getCurrent() * 16 && tot >= 0) {
                this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width - 15, height);
                this.hovered1 = ClickGUIUtils.isHovered(mouseX, mouseY, x + width - 15, y, 15, height);
            }
        } else {
            this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width - 15, height);
            this.hovered1 = ClickGUIUtils.isHovered(mouseX, mouseY, x + width - 15, y, 15, height);
        }
        int modesPanelY = y + height;
        int color = new Color(55, 55, 55, 255).getRGB();
        Helper2D.drawRoundedRectangle(x, y, width, height, 4, color, true, 0);
        if (this.modedr) {
            int modesPanelY1 = 0;
            for (ModeValueSub modesPanel : this.modesPanelList) {
                modesPanelY1 += 16;
            }
            Helper2D.drawRoundedRectangle(x, y + height, width, height + modesPanelY1, 3, color, true, 1);
            int cnt = 0;
            for (ModeValueSub modesPanel : this.modesPanelList) {
                modesPanel.drawScreen(mouseX, mouseY, particalTicks, x - width, modesPanelY, width, 15, tot + ++cnt * 16);
                modesPanelY += 16;
            }
        }
        for (ModeValueSub modesPanel : this.modesPanelList) {
            if (!((ModeSetting) this.setting).getCurrent().equalsIgnoreCase(modesPanel.modes) || ((ModeSetting) this.setting).getCurrent() != "" && ((ModeSetting) this.setting).getCurrent() == modesPanel.modes)
                continue;
            ((ModeSetting) this.setting).setCurrent(modesPanel.modes);
        }
        ModeValuesPart.rectangleBordered(x, y, x + width, modesPanelY, 1.0, new Color(0, 0, 0, 0).getRGB(), new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent(), 200).getRGB());
        if (ClickGui.useFont.getEnable()) {
            FontManager.F16.drawCenteredString(this.setting.getName() + ": " + ((ModeSetting) this.setting).getCurrent(), x + width / 2, y + height / 2 - 3, Color.WHITE.getRGB());
        } else {
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.75f);
            FontUtil.drawCenteredString(this.setting.getName() + ": " + ((ModeSetting) this.setting).getCurrent(), (float) (x + width / 2) * 1.3333334f, (float) (y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
            GL11.glPopMatrix();
        }
        super.drawScreen(mouseX, mouseY, particalTicks, x, y, width, height, tot);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hovered || this.hovered1) {
            this.modedr = !this.modedr;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseClicked1(int mouseX, int mouseY, int mouseButton) {
        for (ModeValueSub modesPanel : this.modesPanelList) {
            modesPanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked1(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}


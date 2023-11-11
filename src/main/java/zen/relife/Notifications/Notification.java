package zen.relife.Notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import zen.relife.Relife;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.util.AnimationUtils;
import zen.relife.util.FontUtil;

import java.awt.*;

public class Notification {
    private final int key;
    public String text;
    public double width = 30.0;
    public double height = 20.0;
    public float x;
    public float y;
    public float position;
    public boolean in = true;
    public AnimationUtils animationUtils = new AnimationUtils();
    Type type;
    AnimationUtils yAnimationUtils = new AnimationUtils();

    public Notification(String text, Type type, int key) {
        this.text = text;
        this.type = type;
        this.key = key;
        this.width = key != 0 ? (ClickGui.useFont.getEnable() ? (double) (FontManager.F16.getStringWidth(text) + 90) : (double) (FontUtil.getStringWidth(text) + 90)) : (ClickGui.useFont.getEnable() ? (double) (FontManager.F16.getStringWidth(text) + 80) : (double) (FontUtil.getStringWidth(text) + 80));
        this.x = (float) this.width;
    }

    public void onRender() {
        int i = 0;
        for (Notification notification : Relife.INSTANCE.notificationManager.notifications) {
            if (notification == this) break;
            ++i;
        }
        this.y = this.yAnimationUtils.animate((float) ((double) i * (this.height + 5.0)), this.y, 0.1f);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        Gui.drawRect((int) ((double) ((float) sr.getScaledWidth() + this.x) - this.width), (int) ((double) ((float) (sr.getScaledHeight() - 55) - this.y) - this.height), (int) ((float) sr.getScaledWidth() + this.x), (int) ((float) (sr.getScaledHeight() - 55) - this.y), new Color(24, 24, 24, 200).getRGB());
        if (this.type.name().equalsIgnoreCase(Type.Enable.name())) {
            if (ClickGui.useFont.getEnable()) {
                FontManager.F18.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
                FontManager.F18.drawString(" - Enabled", (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + 5.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(33, 248, 0, 255).getRGB());
            } else {
                FontUtil.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
                FontUtil.drawString(" - Enabled", (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + 5.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(33, 248, 0, 255).getRGB());
            }
        } else if (this.type.name().equalsIgnoreCase(Type.Disable.name())) {
            if (ClickGui.useFont.getEnable()) {
                FontManager.F18.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
                FontManager.F18.drawString(" - Disabled", (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + 5.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(248, 134, 134, 255).getRGB());
            } else {
                FontUtil.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
                FontUtil.drawString(" - Disabled", (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + 5.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(248, 0, 0, 255).getRGB());
            }
        } else if (this.type.name().equalsIgnoreCase(Type.NONE.name())) {
            if (ClickGui.useFont.getEnable()) {
                FontManager.F18.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
            } else {
                FontUtil.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
            }
        } else if (ClickGui.useFont.getEnable()) {
            FontManager.F18.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
            FontManager.F18.drawString("- BindKey ", (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + 5.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 255).getRGB());
            FontManager.F18.drawString(" " + Keyboard.getKeyName(this.key), (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + (double) FontUtil.getStringWidth("- BindKey ")), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(255, 224, 0, 255).getRGB());
        } else {
            FontUtil.drawString(this.text, (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
            FontUtil.drawString("- BindKey ", (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + 5.0), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 255).getRGB());
            FontUtil.drawString(" " + Keyboard.getKeyName(this.key), (float) ((double) ((float) sr.getScaledWidth() + this.x) - this.width + 10.0 + (double) FontUtil.getStringWidth(this.text) + (double) FontUtil.getStringWidth("- BindKey ")), (float) sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(255, 224, 0, 255).getRGB());
        }
    }

    public enum Type {
        Enable,
        Disable,
        KeyBind,
        NONE

    }
}


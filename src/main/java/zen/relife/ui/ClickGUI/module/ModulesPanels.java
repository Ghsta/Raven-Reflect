package zen.relife.ui.ClickGUI.module;


import org.lwjgl.input.Keyboard;
import zen.relife.Notifications.Notification;
import zen.relife.Relife;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Module;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.setting.ModeSetting;
import zen.relife.setting.Setting;
import zen.relife.ui.ClickGUI.module.setting.BooleanValuePart;
import zen.relife.ui.ClickGUI.module.setting.ModeValuesPart;
import zen.relife.ui.ClickGUI.module.setting.NumberValuePart;
import zen.relife.ui.ClickGUI.module.setting.SettingPanel;
import zen.relife.util.BlurUtil;
import zen.relife.util.ClickGUIUtils;
import zen.relife.util.FontUtil;
import zen.relife.util.Helper2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModulesPanels {
    public final List<SettingPanel> settingPanelList;
    private final Module module;
    public boolean showSetting;
    private boolean hovered;
    private int bik = -2;
    private int y;

    public ModulesPanels(Module module) {
        this.module = module;
        this.settingPanelList = new ArrayList<SettingPanel>();
        if (!module.getSetting().isEmpty()) {
            for (Setting it : module.getSetting()) {
                if (it instanceof EnableSetting) {
                    this.settingPanelList.add(new BooleanValuePart(it));
                    continue;
                }
                if (it instanceof IntegerSetting) {
                    this.settingPanelList.add(new NumberValuePart(it));
                    continue;
                }
                if (!(it instanceof ModeSetting)) continue;
                this.settingPanelList.add(new ModeValuesPart(it));
            }
        }
    }

    public int getSettingY() {
        return this.settingPanelList.size() * 16;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height, int modY, int CategoryY, int tot, Color rainbow) {
        this.y = y;
        this.hovered = ClickGui.Element.getCurrent() != ClickGui.Element.getMax() ? (tot <= (int) ClickGui.Element.getCurrent() * 16 && tot >= 0 && ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height)) : ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        int color = new Color(70, 174, 229).getRGB();
        if (this.bik == -1) {
            if (this.module.getState()) {
                Helper2D.drawRoundedRectangle(x, y, width, height, 6, new Color(59, 56, 56, 255).getRGB(), true, 2);
                Helper2D.drawRoundedRectangle(x, y, width, height, 6, new Color(70, 60, 60, 255).getRGB(), true, 2);
            } else {
                Helper2D.drawRoundedRectangle(x, y, width, height, 6, new Color(168, 239, 255, 157).getRGB(), true, 2);
            }
            if (ClickGui.useFont.getEnable()) {
                FontManager.F16.drawCenteredString("Press a key", x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
            } else {
                FontUtil.drawCenteredString("Press a key", x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
            }
            if (Keyboard.getEventKey() != 42) {
                this.bik = Keyboard.getEventKey();
                if (this.bik != -2 && this.bik != 0) {
                    this.module.setKey(this.bik);
                    Relife.INSTANCE.notificationManager.add(new Notification(this.module.getName(), Notification.Type.KeyBind, this.bik));
                }
            }
        } else {
            this.bik = -2;
            if (this.module.getState()) {
                Helper2D.drawRoundedRectangle(x, y, width, height, 4, new Color(51, 50, 50, 255).getRGB(), true, 0);
                Helper2D.drawRoundedRectangle(x, y, width, height, 4, new Color(51, 50, 50, 255).getRGB(), true, 0);
                Helper2D.drawRoundedRectangle(x, y, width, height, 4, new Color(51, 50, 50, 255).getRGB(), true, 0);
            } else {
                Helper2D.drawRoundedRectangle(x, y, width, height, 4, new Color(42, 38, 38, 255).getRGB(), true, 0);
            }
            if (ClickGui.useFont.getEnable()) {
                if (this.module.getState()) {
                    if ((int) ClickGui.red.getCurrent() > 127 && (int) ClickGui.green.getCurrent() > 127 && (int) ClickGui.blue.getCurrent() > 127) {
                        if (this.module.getKey() != 0) {
                            if (ClickGui.blur.getEnable())
                                BlurUtil.renderBlur(1);
                            FontManager.F16.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, color);
                        } else {
                            FontManager.F16.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, color);
                        }
                    } else if (this.module.getKey() != 0) {
                        FontManager.F16.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
                    } else {
                        FontManager.F16.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
                    }
                } else if (this.module.getKey() != 0) {
                    FontManager.F16.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
                } else {
                    FontManager.F16.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
                }
            } else if (this.module.getState()) {
                if ((int) ClickGui.red.getCurrent() > 127 && (int) ClickGui.green.getCurrent() > 127 && (int) ClickGui.blue.getCurrent() > 127) {
                    if (this.module.getKey() != 0) {
                        if (ClickGui.blur.getEnable())
                            BlurUtil.renderBlur(1);
                        FontUtil.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, color);
                    } else {
                        FontUtil.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, color);
                    }
                } else if (this.module.getKey() != 0) {
                    FontUtil.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
                } else {
                    FontUtil.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
                }
            } else if ((int) ClickGui.red.getCurrent() > 127 && (int) ClickGui.green.getCurrent() > 127 && (int) ClickGui.blue.getCurrent() > 127) {
                if (this.module.getKey() != 0) {
                    FontUtil.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, color);
                } else {
                    FontUtil.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, color);
                }
            } else if (this.module.getKey() != 0) {
                FontUtil.drawCenteredString(this.module.getName() + " <" + Keyboard.getKeyName(this.module.getKey()) + ">", x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
            } else {
                FontUtil.drawCenteredString(this.module.getName(), x + width / 2 - 4, y + height / 2 - 4, Color.WHITE.getRGB());
            }
        }
        if (this.showSetting && !this.settingPanelList.isEmpty()) {
            int settingY = y;
            int cnt = 0;
            for (SettingPanel it : this.settingPanelList) {
                int finalSettingY = settingY;
                it.drawScreen(mouseX, mouseY, particalTicks, x, finalSettingY + 16, width, 16, tot + ++cnt * 16);
                if (it.isModedr()) {
                    settingY += it.getModeY();
                    cnt += it.getModes();
                }
                settingY += 16;
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (ClickGui.blur.getEnable())
            BlurUtil.renderBlur(1);
        if (!this.hovered && this.bik == -1) {
            this.module.setKey(0);
            this.bik = -2;
            Relife.INSTANCE.notificationManager.add(new Notification(this.module.getName(), Notification.Type.KeyBind, 0));
        }
        if (this.hovered && mouseButton == 0) {
            if (Keyboard.isKeyDown(42)) {
                this.bik = -1;
                return;
            }
            this.module.toggle();
        }
        if (this.hovered && mouseButton == 1) {
            boolean bl = this.showSetting = !this.showSetting;
        }
        if (this.showSetting && !this.settingPanelList.isEmpty()) {
            this.settingPanelList.forEach(it -> it.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.showSetting && !this.settingPanelList.isEmpty()) {
            this.settingPanelList.forEach(it -> it.mouseReleased(mouseX, mouseY, state));
        }
    }
}


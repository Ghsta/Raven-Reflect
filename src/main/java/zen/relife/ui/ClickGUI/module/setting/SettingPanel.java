package zen.relife.ui.ClickGUI.module.setting;

import zen.relife.Relife;
import zen.relife.module.Module;
import zen.relife.setting.Setting;
import zen.relife.util.FontUtil;

import java.util.ArrayList;

public class SettingPanel {
    public static boolean a;
    public static int Code;
    public static Setting set;
    public final Setting setting;
    public boolean aBinding;
    public int modeY;
    public int modes;
    public boolean modedr;
    private Module module;

    public SettingPanel(Setting setting) {
        this.setting = setting;
    }

    public boolean isModedr() {
        return this.modedr;
    }

    public void setModedr(boolean modedr) {
        this.modedr = modedr;
    }

    public int getModeY() {
        return this.modeY;
    }

    public int getModes() {
        return this.modes;
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height, int tot) {
    }

    public void mouseClicked1(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public int getMaxSetting() {
        ArrayList<Setting> settings = new ArrayList<Setting>();
        for (Module it : Relife.INSTANCE.getModuleManager().getModules()) {
            for (Setting s : it.getSetting()) {
                settings.addAll(it.getSetting());
            }
        }
        settings.sort((o1, o2) -> FontUtil.getStringWidth(o2.getName()) - FontUtil.getStringWidth(o1.getName()));
        return FontUtil.getStringWidth(settings.get(0).getName()) + 50;
    }
}


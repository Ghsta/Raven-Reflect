package zen.relife.hud.mod.impl;

import net.minecraft.client.gui.ScaledResolution;
import zen.relife.Relife;
import zen.relife.eventbus.handler.SubscribeEvent;
import zen.relife.hud.mod.HudMod;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Module;
import zen.relife.module.impl.render.Hud;
import zen.relife.setting.EnableSetting;
import zen.relife.utils.ColorUtils;

import java.util.ArrayList;
import java.util.Comparator;

public class ModsArrayList extends HudMod {

    int width = new ScaledResolution(mc).getScaledWidth();

    EnableSetting option = Hud.HUD;

    public ModsArrayList() {
        super("ModsArrayList", 100, 100);
    }

    @Override
    public void draw() {
        if (Hud.HUD.getEnable()) {
            ArrayList<Module> enabledModules = new ArrayList<>();
            for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
                if (m.state) {
                    enabledModules.add(m);
                }
            }
            enabledModules.sort(new Comparator<Module>() {
                @Override
                public int compare(Module o1, Module o2) {
                    return FontManager.C18.getStringWidth(o2.getName()) - FontManager.C18.getStringWidth(o1.getName());
                }
            });
            int r = 0;
            int count = 0;
            for (Module m : enabledModules) {
                if (m != null && m.getState()) {
                    int moduleWidth = FontManager.C18.getStringWidth(m.name);
                    FontManager.C18.drawStringWithShadow(m.name, getX() - moduleWidth - 1, getY() + count * FontManager.C18.getHeight() + 2, ColorUtils.rainbow(1) + r);
                    y += FontManager.C18.getHeight();
                    r = r + 10;
                }
                count++;
            }
        }
        super.draw();
    }

    @SubscribeEvent
    public void renderDummy(int mouseX, int mouseY) {
        if (option.getEnable()) {
            ArrayList<Module> enabledModules = new ArrayList<>();
            for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
                if (m.state) {
                    enabledModules.add(m);
                }
            }
            enabledModules.sort(new Comparator<Module>() {
                @Override
                public int compare(Module o1, Module o2) {
                    return FontManager.C18.getStringWidth(o2.getName()) - FontManager.C18.getStringWidth(o1.getName());
                }
            });
            int r = 0;
            int count = 0;
            for (Module m : enabledModules) {
                if (m != null && m.getState()) {
                    int moduleWidth = FontManager.C18.getStringWidth(m.name);
                    FontManager.C18.drawStringWithShadow(m.name, getX() - moduleWidth - 1, getY() + count * FontManager.C18.getHeight() + 2, ColorUtils.rainbow(1) + r);
                    r = r + 10;
                }
                count++;
            }
            super.renderDummy(mouseX, mouseY);
        }
    }
}

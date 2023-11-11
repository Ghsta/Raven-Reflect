package zen.relife.hud.mod.impl;


import zen.relife.Relife;
import zen.relife.eventbus.handler.SubscribeEvent;
import zen.relife.hud.mod.HudMod;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.impl.render.Hud;
import zen.relife.setting.EnableSetting;

import java.awt.*;

public class Sprint extends HudMod {
    EnableSetting option = Hud.SprintRender;

    public Sprint() {
        super("Sprint", 500, 5);
    }

    @Override
    public void draw() {
        if (Relife.INSTANCE.getModuleManager().getModule("Sprint").getState() && Hud.InventoryHUD.getEnable()) {
            FontManager.C16.drawStringWithShadow("Sprinting (Toggled)", getX(), getY(), new Color(255, 255, 255).hashCode());
        } else if (option.getEnable()) {
            FontManager.C16.drawStringWithShadow("Sprinting (Vanilla)", getX(), getY(), new Color(255, 255, 255).hashCode());
        } else if (mc.player.isSneaking() && option.getEnable()) {
            FontManager.C16.drawStringWithShadow("Sneaking (Press)", getX(), getY(), new Color(255, 255, 255).hashCode());
        }
        super.draw();
    }

    @SubscribeEvent
    public void renderDummy(int mouseX, int mouseY) {
        if (Relife.INSTANCE.getModuleManager().getModule("Sprint").getState() && option.getEnable()) {
            FontManager.C16.drawStringWithShadow("Sprinting (Toggled)", getX(), getY(), new Color(255, 255, 255).hashCode());
        } else if (option.getEnable()) {
            FontManager.C16.drawStringWithShadow("Sprinting (Vanilla)", getX(), getY(), new Color(255, 255, 255).hashCode());
        } else if (mc.player.isSneaking() && option.getEnable()) {
            FontManager.C16.drawStringWithShadow("Sneaking (Press)", getX(), getY(), new Color(255, 255, 255).hashCode());
        }
        super.renderDummy(mouseX, mouseY);
    }
}


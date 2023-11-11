package zen.relife.module.impl.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.Relife;
import zen.relife.hud.HUDConfigScreen;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.ModeSetting;
import zen.relife.utils.ColorUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Hud extends Module {
    public static EnableSetting notification = new EnableSetting("Notification", false);
    //HudMods
    public static EnableSetting SprintRender = new EnableSetting("SprintRender", false);
    public static EnableSetting InventoryHUD = new EnableSetting("InventoryHUD", false);
    public static EnableSetting HUD = new EnableSetting("HUD", false);
    public static EnableSetting TargetHUD = new EnableSetting("TargetHUD", false);
    private final ModeSetting mode = new ModeSetting("Mode", "Text", Arrays.asList("NONE", "LOGO"), this);


    public Hud() {
        super("HUD", Keyboard.KEY_H, Category.RENDER, true);
        this.getSetting().add(this.mode);
        this.getSetting().add(notification);
        this.getSetting().add(SprintRender);
        this.getSetting().add(InventoryHUD);
        this.getSetting().add(HUD);
        this.getSetting().add(TargetHUD);
    }

    @SubscribeEvent
    public void Notification(RenderGameOverlayEvent.Text event) {
        if (!notification.getEnable()) {
            return;
        }
        Relife.INSTANCE.notificationManager.draw();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        ScaledResolution s = new ScaledResolution(mc);
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();
        int y = 1;
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiMainMenu)) return;
        if (this.mode.getCurrent().equalsIgnoreCase("Text")) {
            FontManager.Logo.drawStringWithShadow("Relife");
        } else if (this.mode.getCurrent().equalsIgnoreCase("LOGO")) {

            FontManager.Logo.drawStringWithShadow("Relife");
        }

        ArrayList<Module> enabledModules = new ArrayList<Module>();
        for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
            if (!m.state) continue;
            enabledModules.add(m);
        }

        int i = 0;


        if (!(mc.currentScreen instanceof HUDConfigScreen)) {
            Relife.INSTANCE.hudManager.renderMods();
        }
    }
}

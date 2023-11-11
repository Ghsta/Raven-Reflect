package zen.relife.module.impl.render;


import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import zen.relife.Config.ClickGuiConfig;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.ui.ClickGUI.ClickGUI;
import zen.relife.util.Tools;

import java.lang.reflect.Field;

public class ClickGui extends Module {
    public static GuiScreen screen;
    public static IntegerSetting red;
    public static IntegerSetting green;
    public static IntegerSetting blue;
    public static IntegerSetting Element;
    public static IntegerSetting Scale;
    public static EnableSetting blur;
    public static EnableSetting rainBow;
    public static IntegerSetting rainBowSpeed;
    public static EnableSetting useFont;

    static {
        red = new IntegerSetting("Red", 246.0, 0.0, 255.0, 0);
        green = new IntegerSetting("Green", 192.0, 0.0, 255.0, 0);
        blue = new IntegerSetting("Blue", 255.0, 0.0, 255.0, 0);
        Element = new IntegerSetting("MaxElement", 13.0, 1.0, 13.0, 0);
        Scale = new IntegerSetting("Scale", 1.0, 0.5, 2.0, 1);
        blur = new EnableSetting("Blur", false);
        rainBow = new EnableSetting("RainBow", false);
        rainBowSpeed = new IntegerSetting("RainBowSpeed", 50.0, 10.5, 100.0, 1);
        useFont = new EnableSetting("Relife Font", true);
    }

    public ClickGui() {
        super("ClickGui", Keyboard.KEY_RSHIFT, Category.RENDER, false);
        this.getSetting().add(red);
        this.getSetting().add(green);
        this.getSetting().add(blue);
        this.getSetting().add(Element);
        this.getSetting().add(blur);
        this.getSetting().add(useFont);
        this.getSetting().add(rainBow);
        this.getSetting().add(rainBowSpeed);
    }

    public void displayGuiScreenBypass(GuiScreen screen) {
        ClickGui.mc.currentScreen = screen;
        mc.setIngameNotInFocus();
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        try {
            Field screenMCObject = GuiScreen.class.getField("field_146297_k");
            screenMCObject.setAccessible(true);
            screenMCObject.set(screen, mc);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        ClickGui.mc.currentScreen.height = j;
        ClickGui.mc.currentScreen.width = i;
        ClickGui.mc.currentScreen.initGui();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (screen == null) {
            screen = new ClickGUI();
        }
        if (Tools.isSingleplayer()) {
            mc.displayGuiScreen(screen);
        } else {
            this.displayGuiScreenBypass(screen);
        }
        ClickGuiConfig.loadClickGui();
        this.toggle();
    }
}


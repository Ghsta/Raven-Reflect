package zen.relife.module.impl.render;

import org.lwjgl.input.Keyboard;
import zen.relife.manager.impl.ModuleManager;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class Panic extends Module {
    public static boolean isPanic = false;

    public Panic() {
        super("Panic", Keyboard.KEY_INSERT, Category.RENDER, true);
    }

    @Override
    public void onEnable() {
        isPanic = true;

        for (Module m : ModuleManager.modules) {
            if (m != this) {
                m.setToggled(false);
            }
        }
    }

    @Override
    public void disable() {
        isPanic = false;
    }
}

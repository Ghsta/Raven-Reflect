package zen.relife.module.impl.configs;


import zen.relife.Config.*;
import zen.relife.Relife;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class LoadConfig extends Module {
    public LoadConfig() {
        super("LoadConfig", 0, Category.CONFIG, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.toggle();
        try {
            IntegerConfig.loadState();
            EnableConfig.loadState();
            ModeConfig.loadState();
            KeyBindConfig.loadKey();
            ModuleConfig.loadModules();
            ClickGuiConfig.loadClickGui();
            for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
                m.isSilder(null);
                m.setMode(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


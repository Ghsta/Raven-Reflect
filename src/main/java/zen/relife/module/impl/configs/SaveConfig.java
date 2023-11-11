package zen.relife.module.impl.configs;

import zen.relife.Config.*;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.util.Tools;

import java.io.File;

public class SaveConfig extends Module {
    private boolean kp;

    public SaveConfig() {
        super("SaveConfig", 0, Category.CONFIG, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.kp = true;
        this.toggle();
        try {
            File ConfigDir = new File(Tools.getConfigPath());
            if (!ConfigDir.exists()) {
                ConfigDir.mkdir();
            }
            EnableConfig.saveState();
            IntegerConfig.saveState();
            KeyBindConfig.saveKey();
            ModeConfig.saveState();
            ModuleConfig.saveModules();
            ClickGuiConfig.saveClickGui();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}


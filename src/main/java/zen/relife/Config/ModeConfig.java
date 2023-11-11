package zen.relife.Config;

import zen.relife.Relife;
import zen.relife.module.Module;
import zen.relife.setting.ModeSetting;
import zen.relife.setting.Setting;
import zen.relife.util.Tools;

public class ModeConfig {
    private static final ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "5.txt");

    public static void saveState() {
        try {
            configManager.clear();
            for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                if (module.getSettings().isEmpty()) continue;
                for (Setting setting : module.getSettings()) {
                    if (!(setting instanceof ModeSetting)) continue;
                    String line = setting.getName() + ":" + module.getName() + ":" + ((ModeSetting) setting).getCurrent();
                    configManager.write(line);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void loadState() {
        try {
            for (String s : configManager.read()) {
                for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                    if (module.getSettings().isEmpty()) continue;
                    for (Setting setting : module.getSettings()) {
                        String name = s.split(":")[0];
                        String mod = s.split(":")[1];
                        String value = s.split(":")[2];
                        if (!setting.getName().equalsIgnoreCase(name) || !((ModeSetting) setting).getParent().getName().equalsIgnoreCase(mod))
                            continue;
                        ((ModeSetting) setting).setCurrent(value);
                    }
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }
}


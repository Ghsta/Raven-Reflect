package zen.relife.Config;


import zen.relife.Relife;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.Setting;
import zen.relife.util.Tools;

public class EnableConfig {
    private static final ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "2.txt");

    public static void saveState() {
        try {
            configManager.clear();
            for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                if (module.getSettings().isEmpty()) continue;
                for (Setting setting : module.getSettings()) {
                    if (!(setting instanceof EnableSetting)) continue;
                    String line = setting.getName() + ":" + module.getName() + ":" + ((EnableSetting) setting).getEnable();
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
                        boolean enable = false;
                        String mod = s.split(":")[1];
                        boolean toggled = Boolean.parseBoolean(s.split(":")[2]);
                        if (!setting.getName().equalsIgnoreCase(name) || !module.getName().equalsIgnoreCase(mod))
                            continue;
                        ((EnableSetting) setting).setEnable(toggled);
                    }
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }
}


package zen.relife.Config;


import zen.relife.Relife;
import zen.relife.module.Module;
import zen.relife.setting.IntegerSetting;
import zen.relife.setting.Setting;
import zen.relife.util.Tools;

public class IntegerConfig {
    private static final ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "3.txt");

    public static void saveState() {
        try {
            configManager.clear();
            for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                if (module.getSettings().isEmpty()) continue;
                for (Setting setting : module.getSettings()) {
                    if (!(setting instanceof IntegerSetting)) continue;
                    String line = setting.getName() + ":" + module.getName() + ":" + ((IntegerSetting) setting).getCurrent();
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
                        double value = Double.parseDouble(s.split(":")[2]);
                        if (setting.getName().equalsIgnoreCase(name) && module.getName().equalsIgnoreCase(mod)) {
                            ((IntegerSetting) setting).setCurrent(value);
                        }
                        module.isSilder(setting);
                    }
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }
}


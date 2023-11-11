package zen.relife.Config;

import zen.relife.Relife;
import zen.relife.module.Module;
import zen.relife.util.Tools;

public class ModuleConfig {
    private static final ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "6.txt");

    public static void loadModules() {
        try {
            for (String s : configManager.read()) {
                for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                    String name = s.split(":")[0];
                    boolean toggled = Boolean.parseBoolean(s.split(":")[1]);
                    if (!module.getName().equalsIgnoreCase(name) || module.getName() == "ClickGui") continue;
                    module.setState(toggled);
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveModules() {
        try {
            configManager.clear();
            for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                if (module.getName() == "ClickGUI") continue;
                String line = module.getName() + ":" + module.getState();
                configManager.write(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


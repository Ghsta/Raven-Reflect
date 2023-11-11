package zen.relife.Config;

import zen.relife.Relife;
import zen.relife.module.Module;
import zen.relife.util.Tools;

public class KeyBindConfig {
    private static final ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "4.txt");

    public static void saveKey() {
        try {
            configManager.clear();
            for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                String line = module.getName() + ":" + module.getKey();
                configManager.write(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void loadKey() {
        try {
            for (String s : configManager.read()) {
                for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
                    String name = s.split(":")[0];
                    int key = Integer.parseInt(s.split(":")[1]);
                    if (!module.getName().equalsIgnoreCase(name)) continue;
                    module.setKey(key);
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }
}


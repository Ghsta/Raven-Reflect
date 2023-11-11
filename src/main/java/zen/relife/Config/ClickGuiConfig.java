package zen.relife.Config;


import zen.relife.ui.ClickGUI.CategorysPanels;
import zen.relife.ui.ClickGUI.ClickGUI;
import zen.relife.util.Tools;

public class ClickGuiConfig {
    private static final ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "1.txt");

    public static void loadClickGui() {
        for (String s : configManager.read()) {
            String panelName = s.split(":")[0];
            float panelCoordX = Float.parseFloat(s.split(":")[1]);
            float panelCoordY = Float.parseFloat(s.split(":")[2]);
            boolean extended = Boolean.parseBoolean(s.split(":")[3]);
            for (CategorysPanels categorysPanels : ClickGUI.categorysPanels) {
                if (!categorysPanels.category.name().equalsIgnoreCase(panelName)) continue;
                categorysPanels.setX((int) panelCoordX);
                categorysPanels.setY((int) panelCoordY);
                categorysPanels.setOpen(extended);
            }
        }
    }

    public static void saveClickGui() {
        try {
            configManager.clear();
            for (CategorysPanels categorysPanels : ClickGUI.categorysPanels) {
                configManager.write(categorysPanels.category.name() + ":" + categorysPanels.getX() + ":" + categorysPanels.getY() + ":" + categorysPanels.isOpen());
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }
}


package zen.relife.module.impl.misc;

import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.ModeSetting;

import java.util.Arrays;

public class Teams
        extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "ArmorColor", Arrays.asList("Base", "ArmorColor", "NameColor", "TabList"), this);

    public Teams() {
        super("Teams", 0, Category.MISC, false);
        this.getSetting().add(this.mode);
    }
}


package zen.relife.setting;


import zen.relife.module.Module;

public class Setting {
    public final String name;
    private Module parent;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Module getParentMod() {
        return this.parent;
    }
}


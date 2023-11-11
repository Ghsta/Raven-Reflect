package zen.relife.setting;

public class EnableSetting extends Setting {
    private boolean enable;

    public EnableSetting(String name, boolean enable) {
        super(name);
        this.enable = enable;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}


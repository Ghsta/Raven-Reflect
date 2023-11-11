package zen.relife.setting;


import zen.relife.module.Module;

public class IntegerSetting
        extends Setting {
    private final double min;
    private final double max;
    private double current;
    private Module parent;
    private double dou;

    public IntegerSetting(String name, double current, double min, double max, double dou) {
        super(name);
        this.current = current;
        this.min = min;
        this.max = max;
        this.dou = dou;
    }

    public double getDou() {
        return this.dou;
    }

    public void setDou(int dou) {
        this.dou = dou;
    }

    public double getCurrent() {
        return this.current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    @Override
    public Module getParentMod() {
        return this.parent;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }
}


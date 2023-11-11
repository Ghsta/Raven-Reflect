package zen.relife.module;

public enum Category {

    COMBAT("Combat"),
    MOVEMENT("Movement"),

    RENDER("Render"),
    WORLD("World"),
    MISC("Misc"),
    CONFIG("Config"),
    OTHER("Other"),
    EXPLOIT("Explolt");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

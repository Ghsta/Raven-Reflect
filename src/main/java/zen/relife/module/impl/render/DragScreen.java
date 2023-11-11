package zen.relife.module.impl.render;

import zen.relife.hud.HUDConfigScreen;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class DragScreen extends Module {
    public DragScreen() {
        super("DragScreen", 0, Category.RENDER, false);
    }

    @Override
    public void onEnable() {
        this.setState(false);
        mc.player.closeScreen();
        mc.displayGuiScreen(new HUDConfigScreen());
    }
}

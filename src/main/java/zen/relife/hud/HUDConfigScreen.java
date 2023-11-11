package zen.relife.hud;

import net.minecraft.client.gui.GuiScreen;
import zen.relife.Relife;
import zen.relife.hud.mod.HudMod;

public class HUDConfigScreen extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (HudMod m : Relife.INSTANCE.hudManager.hudMods) {
            m.renderDummy(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

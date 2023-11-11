package zen.relife.module.impl.render;


import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.IntegerSetting;

public class ViewModel extends Module {
    IntegerSetting PositionZ = new IntegerSetting("Position Z", -0.45, -10, 10, 1);
    IntegerSetting PositionX = new IntegerSetting("Position X", 500.0, -10, 10, 1);
    IntegerSetting PositionY = new IntegerSetting("Position Y", 0, -10, 10, 1);

    public ViewModel() {
        super("ViewModel", Keyboard.KEY_NONE, Category.RENDER, false);
        this.getSetting().add(this.PositionZ);
        this.getSetting().add(this.PositionX);
        this.getSetting().add(this.PositionY);
    }

    @SubscribeEvent
    public void onRender(RenderSpecificHandEvent e) {
        double x, y, z;
        x = PositionX.getCurrent();
        y = PositionY.getCurrent();
        z = PositionZ.getCurrent();
        GL11.glTranslated(x, y, z);

    }
}


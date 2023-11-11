package zen.relife.module.impl.movement;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class Sprint
        extends Module {
    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT, false);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (Minecraft.getMinecraft().player != null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    }

    public void disable() {
        super.disable();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }
}


package zen.relife.eventbus;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.Relife;
import zen.relife.manager.impl.EventManager;
import zen.relife.module.Module;
import zen.relife.util.Connection;
import zen.relife.util.TimerUtils;
import zen.relife.util.Tools;

public class EventEngine {
    private final TimerUtils timerUtils = new TimerUtils();
    private boolean init;

    public EventEngine() {
        EventManager.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Tools.nullCheck()) {
            this.init = false;
            return;
        }
        if (!this.init) {
            new Connection(this);
            this.init = true;
        }
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
            if (!module.getState() || Minecraft.getMinecraft().world == null) continue;
            suc &= module.onPacket(packet, side.ordinal());
        }
        return suc;
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().currentScreen != null) {
            return;
        }
        int key = Keyboard.getEventKey();
        if (key == 0) {
            return;
        }
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        if (!this.timerUtils.isDelayComplete(10.0)) {
            this.timerUtils.reset();
            return;
        }
        for (Module m : Relife.INSTANCE.getModuleManager().getModules()) {
            if (m.getKey() != Keyboard.getEventKey()) continue;
            m.toggle();
        }
    }
}


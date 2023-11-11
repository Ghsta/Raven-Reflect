package zen.relife;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import zen.relife.eventbus.handler.impl.PlayerKeyEvent;
import zen.relife.eventbus.handler.impl.PlayerUpdateEvent;
import zen.relife.eventbus.handler.impl.Render2DEvent;
import zen.relife.util.KeyboardUtils;

public class ForgeEventListener {
    @SubscribeEvent
    public void onKeyDown(InputEvent.KeyInputEvent event) {
        KeyboardUtils.keyMap.values().forEach(i -> {
            if (i == Keyboard.KEY_NONE) {
                return;
            }
            if (Keyboard.isKeyDown(i)) {
                Relife.INSTANCE.getEventBus().call(new PlayerKeyEvent(i));
            }
        });
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Text event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        Relife.INSTANCE.getEventBus().call(new Render2DEvent(event.getResolution(), event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Relife.INSTANCE.getEventBus().call(new PlayerUpdateEvent());
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.RenderTickEvent event) {
        Display.setTitle("Relife b1 (" + Relife.REVISION + ")");
    }
}

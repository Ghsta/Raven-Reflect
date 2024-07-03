package keystrokesmod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import java.util.ArrayList;
import java.util.List;


public class ForgeEventListener {

    @SubscribeEvent
    public void onClientTick(TickEvent.RenderTickEvent event) {
        Display.setTitle("Raven-Reflect B##$3");
    }
}

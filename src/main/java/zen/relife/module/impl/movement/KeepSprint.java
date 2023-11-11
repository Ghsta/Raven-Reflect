package zen.relife.module.impl.movement;


import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.util.Tools;

public class KeepSprint
        extends Module {
    public KeepSprint() {
        super("KeepSprint", 0, Category.MOVEMENT, false);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (!Tools.currentScreenMinecraft()) {
            return;
        }
        if (!KeepSprint.mc.player.isSprinting()) {
            mc.player.connection.sendPacket(new CPacketPlayer());
            KeepSprint.mc.player.setSprinting(true);
        }
    }
}


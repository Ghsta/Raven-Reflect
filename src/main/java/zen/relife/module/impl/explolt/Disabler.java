package zen.relife.module.impl.explolt;


import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", Keyboard.KEY_NONE, Category.EXPLOIT, false);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
        mc.player.connection.sendPacket(new CPacketClickWindow());
        toggle();
    }
}


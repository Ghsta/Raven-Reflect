package keystrokesmod.client.module.modules.player;

import keystrokesmod.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Module {
   public NoFall() {
      super("NoFall", ModuleCategory.player);
   }

   public void update() {
      if ((double)mc.player.fallDistance > 2.5D) {
         mc.player.connection.sendPacket(new CPacketPlayer(true));
      }

   }
}

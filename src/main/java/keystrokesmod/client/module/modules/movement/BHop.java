package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class BHop extends Module {
   public static SliderSetting a;
   private final double bspd = 0.0025D;

   public BHop() {
      super("Bhop", ModuleCategory.movement);
      this.registerSetting(a = new SliderSetting("Speed", 2.0D, 1.0D, 15.0D, 0.2D));
   }

   public void update() {
      Module fly = Raven.moduleManager.getModuleByClazz(Fly.class);
      if (fly != null && !fly.isEnabled() && Utils.Player.isMoving() && !mc.player.isInWater()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
         mc.player.noClip = true;
         if (mc.player.onGround) {
            mc.player.jump();
         }

         mc.player.setSprinting(true);
         double spd = 0.0025D * a.getInput();
         double m = (float)(Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ) + spd);
         Utils.Player.bop(m);
      }
   }
}

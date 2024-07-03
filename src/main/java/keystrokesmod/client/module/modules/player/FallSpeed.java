package keystrokesmod.client.module.modules.player;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.*;
import keystrokesmod.client.module.modules.movement.Fly;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;

public class FallSpeed extends Module {
   public static DescriptionSetting dc;
   public static SliderSetting a;
   public static TickSetting b;

   public FallSpeed() {
      super("FallSpeed", ModuleCategory.player);
      this.registerSetting(dc = new DescriptionSetting("Vanilla max: 3.92"));
      this.registerSetting(a = new SliderSetting("Motion", 5.0D, 0.0D, 8.0D, 0.1D));
      this.registerSetting(b = new TickSetting("Disable XZ motion", true));
   }

   public void update() {
      if ((double)mc.player.fallDistance >= 2.5D) {
         Module fly = Raven.moduleManager.getModuleByClazz(Fly.class);
         Module noFall = Raven.moduleManager.getModuleByClazz(NoFall.class);

         if (
              (fly != null && fly.isEnabled()) ||
              (noFall != null && noFall.isEnabled())
         ) {
            return;
         }

         if (mc.player.capabilities.isCreativeMode || mc.player.capabilities.isFlying) {
            return;
         }

         if (mc.player.isOnLadder() || mc.player.isInWater() || mc.player.isInLava()) {
            return;
         }

         mc.player.motionY = -a.getInput();
         if (b.isToggled()) {
            mc.player.motionX = mc.player.motionZ = 0.0D;
         }
      }

   }
}

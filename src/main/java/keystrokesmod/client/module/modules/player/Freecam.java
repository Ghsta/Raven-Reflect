package keystrokesmod.client.module.modules.player;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Freecam extends Module {
   public static SliderSetting a;
   public static TickSetting b;
   private final double toRad = 0.017453292519943295D;
   public static EntityOtherPlayerMP en = null;
   private int[] lcc = new int[]{Integer.MAX_VALUE, 0};
   private final float[] sAng = new float[]{0.0F, 0.0F};

   public Freecam() {
      super("Freecam", ModuleCategory.player);
      this.registerSetting(a = new SliderSetting("Speed", 2.5D, 0.5D, 10.0D, 0.5D));
      this.registerSetting(b = new TickSetting("Disable on damage", true));
   }

   @Override
   public void onEnable() {
      if(!Utils.Player.isPlayerInGame()) {
         return;
      }
      if (!mc.player.onGround) {
         this.disable();
      } else {
         en = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
         en.copyLocationAndAnglesFrom(mc.player);
         this.sAng[0] = en.rotationYawHead = mc.player.rotationYawHead;
         this.sAng[1] = mc.player.rotationPitch;
         en.setVelocity(0.0D, 0.0D, 0.0D);
         en.setInvisible(true);
         mc.world.addEntityToWorld(-8008, en);
         mc.setRenderViewEntity(en);
      }
   }

   public void onDisable() {
      if (en != null) {
         mc.setRenderViewEntity(mc.player);
         mc.player.rotationYaw = mc.player.rotationYawHead = this.sAng[0];
         mc.player.rotationPitch = this.sAng[1];
         mc.world.removeEntity(en);
         en = null;
      }

      this.lcc = new int[]{Integer.MAX_VALUE, 0};
      int rg = 1;
      int x = mc.player.chunkCoordX;
      int z = mc.player.chunkCoordZ;

      for(int x2 = -1; x2 <= 1; ++x2) {
         for(int z2 = -1; z2 <= 1; ++z2) {
            int a = x + x2;
            int b = z + z2;
            mc.world.markBlockRangeForRenderUpdate(a * 16, 0, b * 16, a * 16 + 15, 256, b * 16 + 15);
         }
      }

   }

   public void update() {
      if(!Utils.Player.isPlayerInGame() || en == null)
         return;
       if (b.isToggled() && mc.player.hurtTime != 0) {
         this.disable();
      } else {
         mc.player.setSprinting(false);
         mc.player.moveForward = 0.0F;
         mc.player.moveStrafing = 0.0F;
         en.rotationYaw = en.rotationYawHead = mc.player.rotationYaw;
         en.rotationPitch = mc.player.rotationPitch;
         double s = 0.215D * a.getInput();
         EntityOtherPlayerMP var10000;
         double rad;
         double dx;
         double dz;
         if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            rad = (double)en.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            rad = (double)en.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX -= dx;
            var10000 = en;
            var10000.posZ -= dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
            rad = (double)(en.rotationYawHead - 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            rad = (double)(en.rotationYawHead + 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            var10000 = en;
            var10000.posY += 0.93D * s;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
            var10000 = en;
            var10000.posY -= 0.93D * s;
         }

         mc.player.setSneaking(false);
         if (this.lcc[0] != Integer.MAX_VALUE && (this.lcc[0] != en.chunkCoordX || this.lcc[1] != en.chunkCoordZ)) {
            int x = en.chunkCoordX;
            int z = en.chunkCoordZ;
            mc.world.markBlockRangeForRenderUpdate(x * 16, 0, z * 16, x * 16 + 15, 256, z * 16 + 15);
         }

         this.lcc[0] = en.chunkCoordX;
         this.lcc[1] = en.chunkCoordZ;
      }
   }

   @SubscribeEvent
   public void re(RenderWorldLastEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         mc.player.renderArmPitch = mc.player.prevRenderArmPitch = 700.0F;
         Utils.HUD.drawBoxAroundEntity(mc.player, 1, 0.0D, 0.0D, Color.green.getRGB(), false);
         Utils.HUD.drawBoxAroundEntity(mc.player, 2, 0.0D, 0.0D, Color.green.getRGB(), false);
      }

   }

   @SubscribeEvent
   public void m(MouseEvent e) {
      if (Utils.Player.isPlayerInGame() && e.getButton() != -1) {
         e.setCanceled(true);
      }

   }
}

package keystrokesmod.client.module.modules.combat;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.List;

public class Reach extends Module {
   public static DoubleSliderSetting reach;
   public static TickSetting weapon_only;
   public static TickSetting moving_only;
   public static TickSetting sprint_only;
   public static TickSetting hit_through_blocks;

   public Reach() {
      super("Reach", ModuleCategory.combat);
      this.registerSetting(reach = new DoubleSliderSetting("Reach (Blocks)", 3.1, 3.3, 3, 6, 0.05));
      this.registerSetting(weapon_only = new TickSetting("Weapon only", false));
      this.registerSetting(moving_only = new TickSetting("Moving only", false));
      this.registerSetting(sprint_only = new TickSetting("Sprint only", false));
      this.registerSetting(hit_through_blocks = new TickSetting("Hit through blocks", false));
   }

   @SubscribeEvent
   public void onMouse(MouseEvent ev) {
      // legit event
      if(!Utils.Player.isPlayerInGame()) return;
      Module autoClicker = Raven.moduleManager.getModuleByClazz(LeftClicker.class);
      if (autoClicker != null && autoClicker.isEnabled() && Mouse.isButtonDown(0)) return;
      if (ev.getButton() >= 0 && ev.isButtonstate()) {
         call();
      }
   }

   @SubscribeEvent
   public void onRenderTick(TickEvent.RenderTickEvent ev) {
      // autoclick event
      if(!Utils.Player.isPlayerInGame()) return;
      Module autoClicker = Raven.moduleManager.getModuleByClazz(LeftClicker.class);
      if (autoClicker == null || !autoClicker.isEnabled()) return;

      if (autoClicker.isEnabled()  && Mouse.isButtonDown(0)){
         call();
      }
   }

   public static boolean call() {
      if (!Utils.Player.isPlayerInGame()) return false;
      if (weapon_only.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) return false;
      if (moving_only.isToggled() && (double)mc.player.moveForward == 0.0D && (double)mc.player.moveStrafing == 0.0D) return false;
      if (sprint_only.isToggled() && !mc.player.isSprinting()) return false;
      if (!hit_through_blocks.isToggled() && mc.objectMouseOver != null) {
         BlockPos p = mc.objectMouseOver.getBlockPos();
         if (p != null && mc.world.getBlockState(p).getBlock() != Blocks.AIR) {
            return false;
         }
      }

      double r = Utils.Client.ranModuleVal(reach, Utils.Java.rand());
      Object[] o = zz(r, 0.0D);
      if (o == null) {
         return false;
      } else {
         Entity en = (Entity)o[0];
         mc.objectMouseOver = new RayTraceResult(en, (Vec3d)o[1]);
         mc.pointedEntity = en;
         return true;
      }
   }

   private static Object[] zz(double zzD, double zzE) {
      Module reach = Raven.moduleManager.getModuleByClazz(Reach.class);
      if (reach != null && !reach.isEnabled()) {
         zzD = mc.playerController.extendedReach() ? 6.0D : 3.0D;
      }

      Entity entity1 = mc.getRenderViewEntity();
      Entity entity = null;
      if (entity1 == null) {
         return null;
      } else {
         mc.mcProfiler.startSection("pick");
         Vec3d eyes_positions = entity1.getPositionEyes(1.0F);
         Vec3d look = entity1.getLook(1.0F);
         Vec3d new_eyes_pos = eyes_positions.addVector(look.x * zzD, look.x * zzD, look.x * zzD);
         Vec3d zz6 = null;
         List<Entity> zz8 = mc.world.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().grow(look.x * zzD, look.x * zzD, look.x * zzD).expand(1.0D, 1.0D, 1.0D));
         double zz9 = zzD;

         for (Entity o : zz8) {
            if (o.canBeCollidedWith()) {
               float ex = (float) ((double) o.getCollisionBorderSize() * HitBox.exp(o));
               AxisAlignedBB zz13 = o.getEntityBoundingBox().expand(ex, ex, ex);
               zz13 = zz13.expand(zzE, zzE, zzE);
               RayTraceResult zz14 = zz13.calculateIntercept(eyes_positions, new_eyes_pos);
               if (zz13.contains(eyes_positions)) {
                  if (0.0D < zz9 || zz9 == 0.0D) {
                     entity = o;
                     zz6 = zz14 == null ? eyes_positions : zz14.hitVec;
                     zz9 = 0.0D;
                  }
               } else if (zz14 != null) {
                  double zz15 = eyes_positions.distanceTo(zz14.hitVec);
                  if (zz15 < zz9 || zz9 == 0.0D) {
                     if (o == entity1.getRidingEntity()) {
                        if (zz9 == 0.0D) {
                           entity = o;
                           zz6 = zz14.hitVec;
                        }
                     } else {
                        entity = o;
                        zz6 = zz14.hitVec;
                        zz9 = zz15;
                     }
                  }
               }
            }
         }

         if (zz9 < zzD && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
         }

         mc.mcProfiler.endSection();
         if (entity != null && zz6 != null) {
            return new Object[]{entity, zz6};
         } else {
            return null;
         }
      }
   }
}

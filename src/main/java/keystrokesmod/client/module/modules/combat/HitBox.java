package keystrokesmod.client.module.modules.combat;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class HitBox extends Module {
   public static SliderSetting a;
   public static TickSetting b;
   private static RayTraceResult mv;

   public HitBox() {
      super("HitBox", ModuleCategory.combat);
      this.registerSetting(a = new SliderSetting("Multiplier", 1.2D, 1.0D, 5.0D, 0.05D));
      this.registerSetting(b = new TickSetting("Show new hitbox", false));
   }

   public void update() {
      gmo(1.0F);
   }

   @SubscribeEvent
   public void m(MouseEvent e) {
      if(!Utils.Player.isPlayerInGame()) return;
      if (e.getButton() == 0 && e.isButtonstate() && mv != null) {
         mc.objectMouseOver = mv;
      }
   }

   @SubscribeEvent
   public void ef(TickEvent.RenderTickEvent ev) {
      // autoclick event
      if(!Utils.Player.isPlayerInGame()) return;

      Module autoClicker = Raven.moduleManager.getModuleByClazz(LeftClicker.class);
      if(autoClicker != null && !autoClicker.isEnabled()) return;

      if (autoClicker != null && autoClicker.isEnabled() && Mouse.isButtonDown(0)){
         if (mv != null) {
            mc.objectMouseOver = mv;
         }
      }
   }

   @SubscribeEvent
   public void r1(RenderWorldLastEvent e) {
      if (b.isToggled() && Utils.Player.isPlayerInGame()) {
         for (Entity en : mc.world.loadedEntityList) {
            if (en != mc.player && en instanceof EntityLivingBase && ((EntityLivingBase) en).deathTime == 0 && !(en instanceof EntityArmorStand) && !en.isInvisible()) {
               this.rh(en, Color.WHITE);
            }
         }

      }
   }

   public static double exp(Entity en) {
      Module hitBox = Raven.moduleManager.getModuleByClazz(HitBox.class);
      return (hitBox != null && hitBox.isEnabled() && !AntiBot.bot(en)) ? a.getInput() : 1.0D;
   }

   public static void gmo(float partialTicks) {
      if (mc.getRenderViewEntity() != null && mc.world != null) {
         mc.pointedEntity = null;
         Entity pE = null;
         double d0 = 3.0D;
         mv = mc.getRenderViewEntity().rayTrace(d0, partialTicks);
         double d2 = d0;
         Vec3d Vec3d = mc.getRenderViewEntity().getPositionEyes(partialTicks);
         if (mv != null) {
            d2 = mv.hitVec.distanceTo(Vec3d);
         }

         Vec3d vec4 = mc.getRenderViewEntity().getLook(partialTicks);
         Vec3d vec5 = Vec3d.addVector(vec4.x * d0, vec4.y * d0, vec4.z * d0);
         Vec3d vec6 = null;
         float f1 = 1.0F;
         List list = mc.world.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().grow(vec4.x * d0, vec4.y * d0, vec4.z * d0).expand(f1, f1, f1));
         double d3 = d2;

         for (Object o : list) {
            Entity entity = (Entity) o;
            if (entity.canBeCollidedWith()) {
               float ex = (float) ((double) entity.getCollisionBorderSize() * exp(entity));
               AxisAlignedBB ax = entity.getEntityBoundingBox().expand(ex, ex, ex);
               RayTraceResult mop = ax.calculateIntercept(Vec3d, vec5);
               if (ax.contains(Vec3d)) {
                  if (0.0D < d3 || d3 == 0.0D) {
                     pE = entity;
                     vec6 = mop == null ? Vec3d : mop.hitVec;
                     d3 = 0.0D;
                  }
               } else if (mop != null) {
                  double d4 = Vec3d.distanceTo(mop.hitVec);
                  if (d4 < d3 || d3 == 0.0D) {
                     if (entity == mc.getRenderViewEntity().getRidingEntity() && !entity.canRiderInteract()) {
                        if (d3 == 0.0D) {
                           pE = entity;
                           vec6 = mop.hitVec;
                        }
                     } else {
                        pE = entity;
                        vec6 = mop.hitVec;
                        d3 = d4;
                     }
                  }
               }
            }
         }

         if (pE != null && (d3 < d2 || mv == null)) {
            mv = new RayTraceResult(pE, vec6);
            if (pE instanceof EntityLivingBase || pE instanceof EntityItemFrame) {
               mc.pointedEntity = pE;
            }
         }
      }

   }

   private void rh(Entity e, Color c) {
      if (e instanceof EntityLivingBase) {
         double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) Utils.Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
         double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) Utils.Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
         double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) Utils.Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
         float ex = (float)((double)e.getCollisionBorderSize() * a.getInput());
         AxisAlignedBB bbox = e.getEntityBoundingBox().expand(ex, ex, ex);
         AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glLineWidth(2.0F);
         GL11.glColor3d(c.getRed(), c.getGreen(), c.getBlue());
         RenderGlobal.drawSelectionBoundingBox(axis,2,2,3,4);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
      }
   }
}

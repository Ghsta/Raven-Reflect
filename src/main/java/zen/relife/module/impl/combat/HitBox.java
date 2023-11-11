package zen.relife.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zen.relife.Relife;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.setting.ModeSetting;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HitBox extends Module {
    private static RayTraceResult mv;
    private final ModeSetting mode = new ModeSetting("Mode", "Basic", Arrays.asList("Basic", "Box"), this);
    private final IntegerSetting width = new IntegerSetting("Width", 1.0, 0.6, 5.0, 1);
    private final IntegerSetting height = new IntegerSetting("Height", 2.2, 1.8, 5.0, 1);
    private final IntegerSetting expand = new IntegerSetting("Expand", 0.1, 1.0, 2.0, 1);
    private final EnableSetting extra = new EnableSetting("Extra", false);
    private final EnableSetting showBox = new EnableSetting("ShowBox", true);
    int getInput = 2;

    public HitBox() {
        super("HitBox", 0, Category.COMBAT, false);
        this.getSetting().add(this.mode);
        this.getSetting().add(this.width);
        this.getSetting().add(this.height);
        this.getSetting().add(this.expand);
        this.getSetting().add(this.extra);
        this.getSetting().add(this.showBox);
    }

    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        if (entity.width == width && entity.height == height) {
            return;
        }
        entity.width = width;
        entity.height = height;
        double d0 = (double) width / 2.0;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0, entity.posY + (double) entity.height, entity.posZ + d0));
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (event.getButton() == 0 && event.isButtonstate() && mv != null) {
            HitBox.mc.objectMouseOver = mv;
        }
    }


    public void gmo(float partialTicks) {
        if (mc.getRenderViewEntity() != null && HitBox.mc.world != null) {
            HitBox.mc.pointedEntity = null;
            Entity pE = null;
            double d0 = 3.0;
            mv = mc.getRenderViewEntity().rayTrace(d0, partialTicks);
            double d2 = d0;
            Vec3d vec3 = mc.getRenderViewEntity().getPositionEyes(partialTicks);
            if (mv != null) {
                d2 = HitBox.mv.hitVec.distanceTo(vec3);
            }
            Vec3d vec4 = mc.getRenderViewEntity().getLook(partialTicks);
            Vec3d vec5 = vec3.addVector(vec4.x * d0, vec4.y * d0, vec4.z * d0);
            Vec3d vec6 = null;
            float f1 = 1.0f;
            List<Entity> list = HitBox.mc.world.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().expand(vec4.x * d0, vec4.y * d0, vec4.z * d0).expand(f1, f1, f1));
            double d3 = d2;
            for (Entity o : list) {
                double d4;
                Entity entity = o;
                if (!entity.canBeCollidedWith()) continue;
                float ex = (float) ((double) entity.getCollisionBorderSize() * this.exp(entity));
                AxisAlignedBB ax = entity.getEntityBoundingBox().expand(ex, ex, ex);
                RayTraceResult mop = ax.calculateIntercept(vec3, vec5);
                if (mop == null || !((d4 = vec3.distanceTo(mop.hitVec)) < d3) && d3 != 0.0) continue;
                if (entity == HitBox.mc.getRenderViewEntity().getRidingEntity() && !entity.canRiderInteract()) {
                    if (d3 != 0.0) continue;
                    pE = entity;
                    vec6 = mop.hitVec;
                    continue;
                }
                pE = entity;
                vec6 = mop.hitVec;
                d3 = d4;
            }
            if (pE != null && (d3 < d2 || mv == null)) {
                mv = new RayTraceResult(pE, vec6);
                if (pE instanceof EntityLivingBase || pE instanceof EntityItemFrame) {
                    HitBox.mc.pointedEntity = pE;
                }
            }
        }
    }

    private void rh(Entity e, Color c, float partialTicks) {
        if (e instanceof EntityLivingBase) {
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks - HitBox.mc.getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks - HitBox.mc.getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks - HitBox.mc.getRenderManager().viewerPosZ;
            float ex = (float) ((double) e.getCollisionBorderSize() * (double) this.getInput);
            AxisAlignedBB bbox = e.getEntityBoundingBox().expand(ex, ex, ex);
            AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(2.0f);
            GL11.glColor3d(c.getRed(), c.getGreen(), c.getBlue());
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        }
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (entity == HitBox.mc.player) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        return entity.canBeCollidedWith();
    }

    public double exp(Entity en) {
        Module hitBox = Relife.INSTANCE.getModuleManager().getModule("HitBox");
        return hitBox != null && this.getState() ? (double) this.getInput : 1.0;
    }

    class EntitySize {
        public float width;
        public float height;

        public EntitySize(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
}


package zen.relife.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;

import java.util.List;
import java.util.Random;

public class Reach extends Module {
    private static final EnableSetting weapon_only = new EnableSetting("weapon_only", false);
    private static final EnableSetting moving_only = new EnableSetting("moving_only", false);
    private static final EnableSetting sprint_only = new EnableSetting("sprint_only", false);
    private static final EnableSetting hit_through_blocks = new EnableSetting("ThroughBlocks", false);
    public static IntegerSetting maxRange = new IntegerSetting("Max Range", 3.3, 3.0, 6.0, 0.1);
    public static IntegerSetting minRange = new IntegerSetting("Min Range", 3.1, 3.0, 6.0, 0.1);
    private static Random rand;

    public Reach() {
        super("Reach", 0, Category.COMBAT, false);
        this.getSetting().add(maxRange);
        this.getSetting().add(minRange);
        this.getSetting().add(weapon_only);
        this.getSetting().add(moving_only);
        this.getSetting().add(sprint_only);
        rand = new Random();
    }

    public static Object[] doReach(double reachValue, double AABB, float cwc) {
        Entity target = mc.getRenderViewEntity();
        Entity entity = null;
        if (target == null || Reach.mc.world == null) {
            return null;
        }
        Reach.mc.mcProfiler.startSection("pick");
        Vec3d targetEyes = target.getPositionEyes(0.0f);
        Vec3d targetLook = target.getLook(0.0f);
        Vec3d targetVector = targetEyes.addVector(targetLook.x * reachValue, targetLook.y * reachValue, targetLook.z * reachValue);
        Vec3d targetVec = null;
        List<Entity> targetHitbox = Reach.mc.world.getEntitiesWithinAABBExcludingEntity(target, target.getEntityBoundingBox().offset(targetLook.x * reachValue, targetLook.y * reachValue, targetLook.z * reachValue).expand(1.0, 1.0, 1.0));
        double reaching = reachValue;
        for (int i = 0; i < targetHitbox.size(); ++i) {
            double targetHitVec;
            Entity targetEntity = targetHitbox.get(i);
            if (!targetEntity.canBeCollidedWith()) continue;
            float targetCollisionBorderSize = targetEntity.getCollisionBorderSize();
            AxisAlignedBB targetAABB = targetEntity.getEntityBoundingBox().expand(targetCollisionBorderSize, targetCollisionBorderSize, targetCollisionBorderSize);
            targetAABB = targetAABB.expand(AABB, AABB, AABB);
            RayTraceResult tagetPosition = targetAABB.calculateIntercept(targetEyes, targetVector);
            if (targetAABB.contains(targetEyes)) {
                if (!(0.0 < reaching) && reaching != 0.0) continue;
                entity = targetEntity;
                targetVec = tagetPosition == null ? targetEyes : tagetPosition.hitVec;
                reaching = 0.0;
                continue;
            }
            if (tagetPosition == null || !((targetHitVec = targetEyes.distanceTo(tagetPosition.hitVec)) < reaching) && reaching != 0.0)
                continue;
            boolean canRiderInteract = false;
            if (targetEntity == target.getRidingEntity()) {
                if (reaching != 0.0) continue;
                entity = targetEntity;
                targetVec = tagetPosition.hitVec;
                continue;
            }
            entity = targetEntity;
            targetVec = tagetPosition.hitVec;
            reaching = targetHitVec;
        }
        if (reaching < reachValue && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Reach.mc.mcProfiler.endSection();
        if (entity == null || targetVec == null) {
            return null;
        }
        return new Object[]{entity, targetVec};
    }

    @SubscribeEvent
    public void onUpdate(MouseEvent ev) {
        BlockPos blocksReach;
        if (weapon_only.getEnable()) {
            if (Reach.mc.player.getHeldItemMainhand() == null) {
                return;
            }
            if (!(Reach.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(Reach.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
                return;
            }
        }
        if (moving_only.getEnable() && (double) Reach.mc.player.moveForward == 0.0 && (double) Reach.mc.player.moveStrafing == 0.0) {
            return;
        }
        if (sprint_only.getEnable() && !Reach.mc.player.isSprinting()) {
            return;
        }
        if (!hit_through_blocks.getEnable() && Reach.mc.objectMouseOver != null && (blocksReach = Reach.mc.objectMouseOver.getBlockPos()) != null && Reach.mc.world.getBlockState(blocksReach).getBlock() != Blocks.AIR) {
            return;
        }
        double Reach2 = minRange.getCurrent();
        Object[] reachs = Reach.doReach(Reach2, 0.0, 0.0f);
        if (reachs == null) {
            return;
        }
        Reach.mc.objectMouseOver = new RayTraceResult((Entity) reachs[0], (Vec3d) reachs[1]);
        Reach.mc.pointedEntity = (Entity) reachs[0];
    }
}


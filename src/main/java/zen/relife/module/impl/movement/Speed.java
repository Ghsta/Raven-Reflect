package zen.relife.module.impl.movement;

import net.minecraft.util.math.AxisAlignedBB;
import zen.relife.eventbus.eventapi.event.Event;
import zen.relife.eventbus.eventapi.event.EventUpdate;
import zen.relife.eventbus.handler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.setting.ModeSetting;
import zen.relife.util.Move.MoveUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * @author MiLiBlue
 **/
public class Speed extends Module {
    public static final double[] lowhopy = {
            round(0.4, 3, 0.001),
            round(0.71, 3, 0.001),
            round(0.75, 3, 0.001),
            round(0.55, 3, 0.001),
            round(0.41, 3, 0.001),
    };
    public static IntegerSetting Range = new IntegerSetting("Range", 3.1, 3.0, 6.0, 1);
    public ModeSetting mode = new ModeSetting("Mode", "NCP", Arrays.asList("NCP", "Vanilla", "Mineland", "BlocksMC"), this);
    public IntegerSetting timer = new IntegerSetting("Timer", 1.6, 1, 2, 1);
    public EnableSetting workOnDamange = new EnableSetting("OnlyWorkOnDamage", true);
    private boolean bool;
    private int offGroundTicks;
    private double moveSpeed;
    private boolean wasSlow = false;


    public Speed() {
        super("Speed", 0, Category.MOVEMENT, false);
        this.getSetting().add(mode);
        this.getSetting().add(timer);
        this.getSetting().add(workOnDamange);
    }

    public static double round(final double value, final int scale, final double inc) {
        final double halfOfInc = inc / 2.0;
        final double floored = Math.floor(value / inc) * inc;

        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc)
                    .setScale(scale, RoundingMode.HALF_UP)
                    .doubleValue();
        } else {
            return new BigDecimal(floored)
                    .setScale(scale, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }

    @SubscribeEvent
    public void onUpdate(EventUpdate eventUpdate) {
        if (eventUpdate.getType() == Event.Type.POST && mode.getCurrent().equalsIgnoreCase("NCP")) {
            if (mc.player.ticksExisted % 20 <= 9) {

            } else {

            }

            if (MoveUtil.isMoving()) {
                if (mc.player.onGround) {
                    wasSlow = false;
                    mc.player.jump();
                    MoveUtil.strafe(MoveUtil.getSpeed() * 1.01f);
                }
                MoveUtil.strafe(MoveUtil.getSpeed() * 1.0035f);
                if (MoveUtil.getSpeed() < 0.277) {
                    wasSlow = true;
                }
                if (wasSlow) {
                    MoveUtil.strafe(0.277f);
                }


            } else {
                mc.player.motionX = 0.0;
                mc.player.motionZ = 0.0;
                wasSlow = true;
            }
        }
        if (eventUpdate.getType() == Event.Type.PRE && mode.getCurrent().equalsIgnoreCase("Mineland")) {
            final double xDist = mc.player.lastTickPosX - mc.player.posX;
            final double zDist = mc.player.lastTickPosZ - mc.player.posZ;
            final double lastDist = Math.sqrt(xDist * xDist + zDist * zDist);

            if (MoveUtil.isMoving()) {

                // I cannot believe we have six full time developers and not a single one made this speed work in liquid
                // maybe because any of those developers dont give a shit


                if (!mc.player.onGround && mc.player.fallDistance < 0.24) {
                    mc.player.motionY = this.lowHopYModification(mc.player.motionY, round(
                            mc.player.posY - (int) mc.player.posY, 3, 0.001)) + Math.random() / 100000f;
                }


            }

            if (mc.player.collidedHorizontally || mc.player.fallDistance > 0.8) {
                MoveUtil.strafe((float) (MoveUtil.getSpeed() * 0.9));
            }


            if (!isBlockUnder()) {
                MoveUtil.strafe((float) (MoveUtil.getSpeed() * 0.92));
            }

            double motionX2 = mc.player.motionX;
            double motionZ2 = mc.player.motionZ;

            MoveUtil.strafe();

            if (!mc.player.onGround && offGroundTicks != 1) {
                mc.player.motionX = (mc.player.motionX * 3 + motionX2) / 4;
                mc.player.motionZ = (mc.player.motionZ * 3 + motionZ2) / 4;
            }

            mc.player.motionX *= 0.99;
            mc.player.motionZ *= 0.99;
        }
        if (eventUpdate.getType() == Event.Type.POST && mode.getCurrent().equalsIgnoreCase("Vanilla")) {
            if (MoveUtil.isMoving()) {
                if (mc.player.onGround) {
                    mc.player.jump();
                    mc.player.motionX *= 1.01D;
                    mc.player.motionZ *= 1.01D;
                }

                mc.player.motionY -= 0.00099999D;
                moveSpeed = (MoveUtil.getSpeed() * 3);
                MoveUtil.strafe((float) moveSpeed);
            } else {
                mc.player.motionX = 0D;
                mc.player.motionZ = 0D;
            }
        }
        if (mode.getCurrent().equalsIgnoreCase("BlocksMC") && eventUpdate.getType() == Event.Type.POST) {
            if (MoveUtil.isMoving()) {
                if (mc.player.onGround) {
                    mc.player.jump();
                    mc.player.motionX *= 1.01D;
                    mc.player.motionZ *= 1.01D;
                }

                mc.player.motionY -= 0.00099999D;
                moveSpeed = MoveUtil.getSpeed() * 1.001;
                MoveUtil.strafe(moveSpeed);
            } else {
                mc.player.motionX = 0D;
                mc.player.motionZ = 0D;
            }
        }
    }

    public float getMovementDirection(float forward, float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) {
            return yaw;
        }
        boolean reversed = (forward < 0.0F);
        float strafingYaw = 90.0F * ((forward > 0.0F) ? 0.5F : (reversed ? -0.5F : 1.0F));
        if (reversed) {
            yaw += 180.0F;
        }
        if (strafing > 0.0F) {
            yaw -= strafingYaw;
        } else if (strafing < 0.0F) {
            yaw += strafingYaw;
        }
        return yaw;
    }

    private boolean isBlockUnder() {
        if (mc.player.posY < 0) {
            return false;
        }
        for (int off = 0; off < (int) mc.player.posY + 2; off += 2) {
            AxisAlignedBB bb = mc.player.getEntityBoundingBox().offset(0, -off, 0);
            if (!mc.world.getCollisionBoxes(mc.player, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private double lowHopYModification(final double baseMotionY, final double yDistFromGround) {
        if (yDistFromGround == lowhopy[0]) {
            return 0.31;
        } else if (yDistFromGround == lowhopy[1]) {
            return 0.04;
        } else if (yDistFromGround == lowhopy[2]) {
            return -0.2;
        } else if (yDistFromGround == lowhopy[3]) {
            return -0.14;
        } else if (yDistFromGround == lowhopy[4]) {
            return -0.2;
        }
        return baseMotionY;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void disable() {
        mc.player.jumpMovementFactor = 0.02f;

    }
}

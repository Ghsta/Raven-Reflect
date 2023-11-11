package zen.relife.module.impl.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.util.FriendsUtil;

import java.util.ArrayList;
import java.util.List;

public class KillAura extends Module {

    private static final EnableSetting Rotation = new EnableSetting("OnlyCritical", false);
    private static final EnableSetting Silent = new EnableSetting("Silent", true);
    private static final EnableSetting Walls = new EnableSetting("Walls", false);
    private static final EnableSetting Players = new EnableSetting("Players", false);
    private static final EnableSetting Mobs = new EnableSetting("Mobs", false);
    private static final EnableSetting Invisibles = new EnableSetting("Invisibles", false);
    private static final EnableSetting IgnoreFriends = new EnableSetting("IgnoreFriends", false);
    public static ArrayList<EntityLivingBase> targets = new ArrayList();
    public static EntityLivingBase target = null;
    public static IntegerSetting Range = new IntegerSetting("Range", 3.1, 3.0, 6.0, 2);
    public static IntegerSetting CriticalFallDistance = new IntegerSetting("CriticalFallDistance", 0.2, 0.2, 0.4, 1);
    private static float yaw;
    private static float pitch;
    public ArrayList<EntityLivingBase> attackedTargets = new ArrayList();

    public KillAura() {
        super("KillAura", Keyboard.KEY_NONE, Category.COMBAT, false);
        this.getSetting().add(Range);
        this.getSetting().add(Rotation);
        this.getSetting().add(CriticalFallDistance);
        this.getSetting().add(Walls);
        this.getSetting().add(Players);
        this.getSetting().add(Mobs);
        this.getSetting().add(Invisibles);
        this.getSetting().add(IgnoreFriends);
    }

    public static boolean canSeeEntityAtFov(Entity entityIn, float scope) {
        double diffX = entityIn.posX - mc.player.posX;
        double diffZ = entityIn.posZ - mc.player.posZ;
        float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double difference = angleDifference(newYaw, mc.player.rotationYaw);
        return difference <= scope;
    }

    public static double angleDifference(double a, double b) {
        float yaw360 = (float) (Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }

    public static float[] getRotationToEntity(Entity target) {
        Minecraft.getMinecraft();
        double xDiff = target.posX - mc.player.posX;
        Minecraft.getMinecraft();
        double yDiff = target.posY - mc.player.posY;
        Minecraft.getMinecraft();
        double zDiff = target.posZ - mc.player.posZ;
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        float pitch = (float) (-Math.atan2(target.posY + (double) target.getEyeHeight() / 0.0 - (mc.player.posY + (double) mc.player.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        if (yDiff > -0.2 && yDiff < 0.2) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            pitch = (float) (-Math.atan2(target.posY + (double) target.getEyeHeight() / HitLocation.CHEST.getOffset() - (mc.player.posY + (double) mc.player.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        } else if (yDiff > -0.2) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            pitch = (float) (-Math.atan2(target.posY + (double) target.getEyeHeight() / HitLocation.FEET.getOffset() - (mc.player.posY + (double) mc.player.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        } else if (yDiff < 0.3) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            pitch = (float) (-Math.atan2(target.posY + (double) target.getEyeHeight() / HitLocation.HEAD.getOffset() - (mc.player.posY + (double) mc.player.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        return new float[]{yaw, pitch};
    }

    public static float getYawDifference(float current, float target) {
        float rot = 0;
        return rot + ((rot = (target + 180.0f - current) % 360.0f) > 0.0f ? -180.0f : 180.0f);
    }

    public static EntityLivingBase getSortEntities() {
        double range = Range.getCurrent();

        List<EntityLivingBase> entity = new ArrayList<>();
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) e;
                if (mc.player.getDistance(player) < range && (canAssist(player))) {
                    if (player.getHealth() > 0) {
                        entity.add(player);
                    } else {
                        entity.remove(player);
                    }
                }
            }
        }

        entity.sort((o1, o2) -> (int) (o1.getHealth() - o2.getHealth()));

        if (entity.isEmpty())
            return null;

        return entity.get(0);
    }

    public static boolean canAssist(EntityLivingBase player) {
        boolean players = Players.getEnable();
        boolean walls = Walls.getEnable();
        boolean invisibles = Invisibles.getEnable();
        boolean mobs = Mobs.getEnable();

        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !players) {
                return false;
            }
            if (player instanceof EntityAnimal && !mobs) {
                return false;
            }
            if (player instanceof EntityMob && !mobs) {
                return false;
            }
            if (player instanceof EntityVillager && !mobs) {
                return false;
            }
        }
        if (player.isInvisible() && !invisibles) {
            return false;
        }
        if (!canSeeEntityAtFov(player, 90)) {
            return false;
        }
        if (!player.canEntityBeSeen(mc.player)) {
            return walls;
        }
        if (player instanceof EntityArmorStand) {
            return false;
        }
        return player != mc.player;
    }

    public static float UpdateRotation(float current, float newValue, float speed) {
        float f = MathHelper.wrapDegrees(newValue - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }

    public static float getFixedRotation(float rot) {
        return getDeltaMouse(rot) * getGCDValue();
    }

    public static float getGCDValue() {
        return (float) (getGCD() * 0.15);
    }

    public static float getGCD() {
        float f1;
        return (f1 = (float) (mc.gameSettings.mouseSensitivity * 0.6 + 0.2)) * f1 * f1 * 8;
    }

    public static float getDeltaMouse(float delta) {
        return Math.round(delta / getGCDValue());
    }

    public static float randomizeFloat(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }

    public float getYawDifference(float yaw, EntityLivingBase target) {
        return KillAura.getYawDifference(yaw, KillAura.getRotationToEntity(target)[0]);
    }

    private void doRotation(EntityLivingBase entity) {
        float[] rots = getRotationsForAssist(entity);
        if (canAssist(entity) && entity.getHealth() > 0) {
            yaw = rots[0];
            pitch = rots[1];
            if (!Silent.getEnable()) {
                mc.player.rotationYaw = yaw;
                mc.player.rotationPitch = pitch;
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        double range = Range.getCurrent();
        boolean onlycriticals = Rotation.getEnable();
        double criticalsfalldistance = CriticalFallDistance.getCurrent();
        boolean ignorefriends = IgnoreFriends.getEnable();

        EntityLivingBase entity = getSortEntities();
        if (entity != null) {
            for (EntityPlayer playerEntity : mc.world.playerEntities) {
                if (playerEntity != null && playerEntity != mc.player) {
                    if (entity == playerEntity && FriendsUtil.isFriend((EntityPlayer) entity) && ignorefriends) {
                    } else {
                        if (mc.player.getDistance(entity) <= range) {
                            if (entity != mc.player) {
                                doRotation(entity);
                            }
                        }
                        if (!AutoDestroy.destroy) {
                            if (onlycriticals) {
                                if (mc.player.motionY <= -criticalsfalldistance && mc.player.posY != mc.player.prevPosY) {
                                    if (mc.player.getCooledAttackStrength(0) == 1) {
                                        mc.playerController.attackEntity(mc.player, entity);
                                        mc.player.swingArm(EnumHand.MAIN_HAND);
                                        mc.player.resetCooldown();
                                    }
                                }
                            } else {
                                if (mc.player.getCooledAttackStrength(0) == 1) {
                                    mc.playerController.attackEntity(mc.player, entity);
                                    mc.player.swingArm(EnumHand.MAIN_HAND);
                                    mc.player.resetCooldown();
                                }
                            }
                        }
                    }
                } else {
                    if (mc.player.getDistance(entity) <= range) {
                        if (entity != mc.player) {
                            float[] rots = getRotationsForAssist(entity);
                            if (canAssist(entity) && entity.getHealth() > 0) {
                                mc.player.rotationYaw = rots[0];
                                mc.player.rotationPitch = rots[1];
                            }
                        }
                    }
                    if (!AutoDestroy.destroy) {
                        if (onlycriticals) {
                            if (mc.player.motionY <= -criticalsfalldistance && mc.player.posY != mc.player.prevPosY) {
                                if (mc.player.getCooledAttackStrength(0) == 1) {
                                    mc.playerController.attackEntity(mc.player, entity);
                                    mc.player.swingArm(EnumHand.MAIN_HAND);
                                    mc.player.resetCooldown();
                                }
                            }
                        } else {
                            if (mc.player.getCooledAttackStrength(0) == 1) {
                                mc.playerController.attackEntity(mc.player, entity);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.resetCooldown();
                            }
                        }
                    }
                }
            }
        }
    }

    private float[] getRotationsForAssist(EntityLivingBase entityIn) {
        float yaw = UpdateRotation(getFixedRotation(mc.player.rotationYaw + randomizeFloat(-1, 1)), getRotation(entityIn)[0], 1F * 10);
        float pitch = UpdateRotation(getFixedRotation(mc.player.rotationPitch + randomizeFloat(-1, 1)), getRotation(entityIn)[1], 1F * 10);
        return new float[]{yaw, pitch};
    }

    private float[] getRotation(Entity e) {
        float aimPoint;
        aimPoint = 0.5f;

        double xDelta = e.posX + (e.posX - e.prevPosX) * (float) 0.5 - mc.player.posX - mc.player.motionX * (float) 0.5;
        double zDelta = e.posZ + (e.posZ - e.prevPosZ) * (float) 0.5 - mc.player.posZ - mc.player.motionZ * (float) 0.5;
        double diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight() + aimPoint);

        double distance = MathHelper.sqrt(xDelta * xDelta + zDelta * zDelta);

        float yaw = (float) ((MathHelper.atan2(zDelta, xDelta) * 180.0D / Math.PI) - 90.0F) + randomizeFloat(-1F, 1F);
        float pitch = ((float) (-(MathHelper.atan2(diffY, distance) * 180.0D / Math.PI))) + randomizeFloat(-1F, 1F);

        yaw = (mc.player.rotationYaw + getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw)));
        pitch = mc.player.rotationPitch + getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90F, 90F);
        return new float[]{yaw, pitch};
    }

    enum HitLocation {
        AUTO(0.0), HEAD(1.0), CHEST(1.5), FEET(3.5);

        public double offset;

        HitLocation(double offset) {
            this.offset = offset;
        }

        public double getOffset() {
            return this.offset;
        }
    }
}

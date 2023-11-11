package zen.relife.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class PlayerUtils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isNotMoving() {
        return mc.player.moveForward == 0 && mc.player.moveStrafing == 0;
    }

    public static boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }


    public static float[] aimAtLocation(final double x, final double y, final double z, final EnumFacing facing) {
        final EntitySnowball temp = new EntitySnowball(mc.world);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        final EntitySnowball entitySnowball = temp;
        entitySnowball.posX += facing.getDirectionVec().getX() * 0.25;
        final EntitySnowball entitySnowball2 = temp;
        entitySnowball2.posY += facing.getDirectionVec().getY() * 0.25;
        final EntitySnowball entitySnowball3 = temp;
        entitySnowball3.posZ += facing.getDirectionVec().getZ() * 0.25;
        return aimAtLocation(temp.posX, temp.posY, temp.posZ);
    }

    public static float[] aimAtLocation(final double positionX, final double positionY, final double positionZ) {
        final double x = positionX - mc.player.posX;
        final double y = positionY - mc.player.posY;
        final double z = positionZ - mc.player.posZ;
        final double distance = net.minecraft.util.math.MathHelper.sqrt(x * x + z * z);
        return new float[]{(float) (Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f,
                (float) (-(Math.atan2(y, distance) * 180.0 / 3.141592653589793))};
    }

    public static float getDirection() {
        float yaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (mc.player.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        yaw *= 0.017453292f;
        return yaw;
    }

    public static void toFwd(final double speed) {
        final float yaw = mc.player.rotationYaw * 0.017453292f;
        final EntityPlayerSP player = mc.player;
        player.motionX -= MathHelper.round(yaw) * speed;
        final EntityPlayerSP thePlayer2 = mc.player;
        thePlayer2.motionZ += MathHelper.round(yaw) * speed;
    }

    public static double getSpeed() {
        return getSpeed(mc.player);
    }

    public static void setSpeed(final double speed) {
        mc.player.motionX = -(Math.sin(getDirection()) * speed);
        mc.player.motionZ = Math.cos(getDirection()) * speed;
    }

    public static double getSpeed(Entity entity) {
        return Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
    }

    public static Block getBlockUnderPlayer(final EntityPlayer inPlayer) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0, inPlayer.posZ));
    }

    public static Block getBlock(final BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock();
    }

    public static Block getBlockAtPosC(final EntityPlayer inPlayer, final double x, final double y, final double z) {
        return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }

    public static ArrayList<Vector3f> vanillaTeleportPositions(final double tpX, final double tpY, final double tpZ,
                                                               final double speed) {
        final ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
        final double posX = tpX - mc.player.posX;
        final double posY = tpY - (mc.player.posY + mc.player.getEyeHeight() + 1.1);
        final double posZ = tpZ - mc.player.posZ;
        final float yaw = (float) (Math.atan2(posZ, posX) * 180.0 / 3.141592653589793 - 90.0);
        final float pitch = (float) (-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0
                / 3.141592653589793);
        double tmpX = mc.player.posX;
        double tmpY = mc.player.posY;
        double tmpZ = mc.player.posZ;
        double steps = 1.0;
        for (double d = speed; d < getDistance(mc.player.posX, mc.player.posY, mc.player.posZ, tpX, tpY,
                tpZ); d += speed) {
            ++steps;
        }
        for (double d = speed; d < getDistance(mc.player.posX, mc.player.posY, mc.player.posZ, tpX, tpY,
                tpZ); d += speed) {
            tmpX = mc.player.posX - Math.sin(getDirection(yaw)) * d;
            tmpZ = mc.player.posZ + Math.cos(getDirection(yaw)) * d;
            tmpY -= (mc.player.posY - tpY) / steps;
            positions.add(new Vector3f((float) tmpX, (float) tmpY, (float) tmpZ));
        }
        positions.add(new Vector3f((float) tpX, (float) tpY, (float) tpZ));
        return positions;
    }

    public static float getDirection(float yaw) {
        if (mc.player.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (mc.player.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        yaw *= 0.017453292f;
        return yaw;
    }

    public static double getDistance(final double x1, final double y1, final double z1, final double x2,
                                     final double y2, final double z2) {
        final double d0 = x1 - x2;
        final double d2 = y1 - y2;
        final double d3 = z1 - z2;
        return net.minecraft.util.math.MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }

    public static boolean MovementInput() {
        return mc.gameSettings.keyBindForward.isPressed() || mc.gameSettings.keyBindLeft.isPressed()
                || mc.gameSettings.keyBindRight.isPressed() || mc.gameSettings.keyBindBack.isPressed();
    }

    public static ResourceLocation getskin(EntityLivingBase entity) {
        ResourceLocation var2;
        if (entity instanceof EntityPlayer) {
            NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(entity.getUniqueID());
            var2 = playerInfo.getLocationSkin();
        } else {
            var2 = DefaultPlayerSkin.getDefaultSkinLegacy();
        }

        return var2;
    }

    public static boolean hotbarIsFull() {
        for (int i = 0; i <= 36; ++i) {
            ItemStack itemstack = mc.player.inventory.getStackInSlot(i);

            if (itemstack == null) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMoving() {
        if ((!mc.player.collidedHorizontally) && (!mc.player.isSneaking())) {
            return ((mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }

    public static boolean isMoving2() {
        return ((mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F));
    }

    public static void blinkToPos(double[] startPos, BlockPos endPos, double slack, double[] pOffset) {
        double curX = startPos[0];
        double curY = startPos[1];
        double curZ = startPos[2];
        double endX = (double) endPos.getX() + 0.5D;
        double endY = (double) endPos.getY() + 1.0D;
        double endZ = (double) endPos.getZ() + 0.5D;
        double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);

        for (int count = 0; distance > slack; ++count) {
            distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            if (count > 120) {
                break;
            }

            boolean next = false;
            double diffX = curX - endX;
            double diffY = curY - endY;
            double diffZ = curZ - endZ;
            double offset = (count & 1) == 0 ? pOffset[0] : pOffset[1];
            if (diffX < 0.0D) {
                if (Math.abs(diffX) > offset) {
                    curX += offset;
                } else {
                    curX += Math.abs(diffX);
                }
            }

            if (diffX > 0.0D) {
                if (Math.abs(diffX) > offset) {
                    curX -= offset;
                } else {
                    curX -= Math.abs(diffX);
                }
            }

            if (diffY < 0.0D) {
                if (Math.abs(diffY) > 0.25D) {
                    curY += 0.25D;
                } else {
                    curY += Math.abs(diffY);
                }
            }

            if (diffY > 0.0D) {
                if (Math.abs(diffY) > 0.25D) {
                    curY -= 0.25D;
                } else {
                    curY -= Math.abs(diffY);
                }
            }

            if (diffZ < 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    curZ += offset;
                } else {
                    curZ += Math.abs(diffZ);
                }
            }

            if (diffZ > 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    curZ -= offset;
                } else {
                    curZ -= Math.abs(diffZ);
                }
            }

        }

    }

    public void portMove(float yaw, float multiplyer, float up) {
        double moveX = -Math.sin(Math.toRadians(yaw)) * (double) multiplyer;
        double moveZ = Math.cos(Math.toRadians(yaw)) * (double) multiplyer;
        double moveY = up;
        mc.player.setPosition(moveX + mc.player.posX, moveY + mc.player.posY,
                moveZ + mc.player.posZ);
    }

}
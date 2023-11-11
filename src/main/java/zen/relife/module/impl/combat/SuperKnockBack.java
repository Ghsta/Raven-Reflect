package zen.relife.module.impl.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.IntegerSetting;
import zen.relife.util.chat.ChatUtils;

import java.lang.reflect.Field;

public class SuperKnockBack extends Module {
    private final IntegerSetting hurtTime = new IntegerSetting("HurtTime", 10.0, 0.0, 10.0, 1);
    Field c = null;

    public SuperKnockBack() {
        super("SuperKnockBack", 0, Category.COMBAT, false);
        this.getSetting().add(this.hurtTime);
    }

    @SubscribeEvent
    public void onUpdate(AttackEntityEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            if ((double) event.getEntityLiving().hurtTime > this.hurtTime.getCurrent()) {
                return;
            }
            if (SuperKnockBack.mc.player.isSprinting()) {
                SuperKnockBack.mc.player.setSprinting(true);
            }
            mc.player.connection.sendPacket(new CPacketEntityAction(SuperKnockBack.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            mc.player.connection.sendPacket(new CPacketEntityAction(SuperKnockBack.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            mc.player.connection.sendPacket(new CPacketEntityAction(SuperKnockBack.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            mc.player.connection.sendPacket(new CPacketPlayer(mc.player.handleWaterMovement()));
            try {
                Class<?> ca = SuperKnockBack.mc.player.getClass();
                Field field = ca.getDeclaredField("serverSprintState");
                field.setAccessible(true);
                field.setBoolean(SuperKnockBack.mc.player, true);
            } catch (Exception d) {
                ChatUtils.error(d);
            }
        }
    }
}


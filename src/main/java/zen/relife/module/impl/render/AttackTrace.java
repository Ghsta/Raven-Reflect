package zen.relife.module.impl.render;

import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.util.RenderUtils;

public class AttackTrace extends Module {
    public static Entity attackEntity = null;

    public AttackTrace() {
        super("AttackTrace", Keyboard.KEY_NONE, Category.RENDER, false);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (attackEntity != null && !attackEntity.isDead) {
            if (mc.player.getDistance(attackEntity) < 10) {
                RenderUtils.trace(mc, attackEntity, mc.getRenderPartialTicks(), 0);
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(AttackEntityEvent e) {
        attackEntity = e.getTarget();
    }
}

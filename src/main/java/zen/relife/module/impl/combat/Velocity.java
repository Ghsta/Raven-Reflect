package zen.relife.module.impl.combat;


import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.IntegerSetting;
import zen.relife.util.Tools;

public class Velocity
        extends Module {
    private final IntegerSetting Horizontal = new IntegerSetting("Horizontal", 100.0, 0.0, 100.0, 1);
    private final IntegerSetting Vertical = new IntegerSetting("Vertical", 100.0, 0.0, 100.0, 1);

    public Velocity() {
        super("Velocity", 0, Category.COMBAT, false);
        this.getSetting().add(this.Horizontal);
        this.getSetting().add(this.Vertical);
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent ev) {
        if (Tools.isPlayerInGame() && Velocity.mc.player.maxHurtTime > 0 && Velocity.mc.player.hurtTime == Velocity.mc.player.maxHurtTime) {
            if (this.Horizontal.getCurrent() != 100.0) {
                Velocity.mc.player.motionX *= this.Horizontal.getCurrent() / 100.0;
                Velocity.mc.player.motionZ *= this.Horizontal.getCurrent() / 100.0;
            }
            if (this.Vertical.getCurrent() != 100.0) {
                Velocity.mc.player.motionY *= this.Vertical.getCurrent() / 100.0;
            }
        }
    }
}


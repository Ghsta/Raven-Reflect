package zen.relife.module.impl.movement;


import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.eventbus.handler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;

/**
 * @author MiLiBlue
 **/
public class Fly extends Module {
    public Fly() {
        super("Fly", 0, Category.MOVEMENT, false);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        e.player.inventory.player.motionY = 2.0f;
    }
}

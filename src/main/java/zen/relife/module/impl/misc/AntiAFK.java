package zen.relife.module.impl.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.IntegerSetting;
import zen.relife.util.TimerUtils;
import zen.relife.util.Tools;

public class AntiAFK
        extends Module {
    private final TimerUtils timerUtils = new TimerUtils();
    private final IntegerSetting Delay = new IntegerSetting("Delay", 10.0, 1.0, 100.0, 0);

    public AntiAFK() {
        super("AntiAFK", 0, Category.MISC, false);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (this.timerUtils.isDelayComplete(2000.0)) {
            AntiAFK.mc.player.jump();
            this.timerUtils.reset();
        }
    }
}


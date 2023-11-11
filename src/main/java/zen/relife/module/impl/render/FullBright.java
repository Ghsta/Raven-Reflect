package zen.relife.module.impl.render;


import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.util.Tools;

public class FullBright extends Module {
    private float old;

    public FullBright() {
        super("FullBright", 0, Category.RENDER, false);
    }

    @Override
    public void onEnable() {
        this.old = FullBright.mc.gameSettings.gammaSetting;
        super.onEnable();
    }

    @Override
    public void disable() {
        super.onEnable();
        FullBright.mc.gameSettings.gammaSetting = this.old;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            this.disable();
            return;
        }
        if (FullBright.mc.gameSettings.gammaSetting != 10000.0f) {
            FullBright.mc.gameSettings.gammaSetting = 10000.0f;
        }
    }
}


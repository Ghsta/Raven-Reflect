package zen.relife.module.impl.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.IntegerSetting;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ClickAssist
        extends Module {
    private final IntegerSetting MinCPS = new IntegerSetting("MinCPS", 4.0, 8.0, 20.0, 1);
    private final IntegerSetting MaxCPS = new IntegerSetting("MaxCPS", 12, 5.0, 20.0, 1);
    private final ArrayList cps = new ArrayList();
    public double minLeft;
    public double maxLeft;
    private long lastClick;
    private long hold;
    private double speed;
    private double holdLength;
    private int averageCPS = 0;
    private boolean wasClick = false;

    public ClickAssist() {
        super("ClickAssist", 0, Category.COMBAT, false);
        this.getSetting().add(MaxCPS);
        this.getSetting().add(MinCPS);
    }

    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent renderTickEvent) {
        if (ClickAssist.mc.player == null) {
            return;
        }
        Mouse.poll();
        this.updateVals();
        if (this.cps.size() > Math.max(4, this.averageCPS) && ThreadLocalRandom.current().nextDouble(this.minLeft - 0.2, this.maxLeft) > (double) this.cps.size()) {
            if ((double) (System.currentTimeMillis() - this.lastClick) > this.speed * 1000.0) {
                this.lastClick = System.currentTimeMillis();
                if (this.hold < this.lastClick) {
                    this.hold = this.lastClick;
                }
                int n = ClickAssist.mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(n, true);
                KeyBinding.onTick(n);
                this.updateVals();
            } else if ((double) (System.currentTimeMillis() - this.hold) > this.holdLength * 1000.0) {
                KeyBinding.setKeyBindState(ClickAssist.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                this.updateVals();
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.wasClick = false;
        this.averageCPS = 0;
    }

    private void updateVals() {
        int n = 0;
        if (n < this.cps.size()) {
            if (System.currentTimeMillis() - (Long) this.cps.get(n) > 1000L) {
                this.cps.remove(n);
            }
            ++n;
            return;
        }
        if (!this.wasClick && Mouse.isButtonDown(0)) {
            this.cps.add(System.currentTimeMillis());
            this.averageCPS = (int) ((double) this.cps.size() / 1.3);
            this.wasClick = true;
        } else if (!Mouse.isButtonDown(0)) {
            this.wasClick = false;
        }
        if (this.minLeft >= this.maxLeft) {
            this.maxLeft = this.minLeft + 1.0;
        }
        this.speed = 1.0 / ThreadLocalRandom.current().nextDouble(this.minLeft - 0.2, this.maxLeft);
        this.holdLength = this.speed / ThreadLocalRandom.current().nextDouble(this.minLeft, this.maxLeft);
    }
}


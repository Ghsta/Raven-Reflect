package zen.relife.module.impl.movement;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zen.relife.eventbus.handler.impl.LivingEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.util.TimerUtils;
import zen.relife.utils.Game;

public class Eagle extends Module {
    private final TimerUtils sneakTimer = new TimerUtils();
    IntegerSetting minDelay = new IntegerSetting("Min Delay", 500.0, 0.0, 1500.0, 1);
    IntegerSetting maxDelay = new IntegerSetting("Max Delay", 500.0, 0.0, 1500.0, 1);
    EnableSetting onKey = new EnableSetting("OnSneakKey", true);
    EnableSetting mode = new EnableSetting("Eagle", true);
    EnableSetting debug = new EnableSetting("Debug", false);

    public Eagle() {
        super("Eagle", 0, Category.MOVEMENT, false);
        this.getSetting().add(mode);
        this.getSetting().add(this.minDelay);
        this.getSetting().add(this.maxDelay);
        this.getSetting().add(this.onKey);
        this.getSetting().add(this.debug);
    }

    @Override
    public void onTick() {
        if (mc.player == null || Game.World() == null || !Game.Player().onGround) {
            return;
        }
        double d0 = (double) ((int) Game.Player().posX) - Game.Player().posX;
        double d1 = Game.Player().posZ - (double) ((int) Game.Player().posZ);

        System.out.println(d0 + " " + d1);
        if (Game.World().getBlockState((new BlockPos(Game.Player())).add(0, -1, 0)).getBlock() == Blocks.AIR) {
            Eagle.mc.player.motionX = 0.0D;
            Eagle.mc.player.motionY = 0.0D;
            Eagle.mc.player.motionZ = 0.0D;
            Eagle.mc.player.jumpMovementFactor = 0.0F;
            Eagle.mc.player.noClip = true;
            Eagle.mc.player.onGround = false;
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent livingUpdateEvent) {
    }

    @Override
    public void disable() {
    }
}



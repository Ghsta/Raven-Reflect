package zen.relife.module.impl.world;

import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.util.BlockUtil;
import zen.relife.util.PlayerUtil;
import zen.relife.util.Relife1;

public class FastBreak extends Module {
    public FastBreak() {
        super("FastBreak", 0, Category.WORLD, false);
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent e) {
        PlayerUtil.setBlockHitDelay(0);
    }

    @SubscribeEvent
    public void onLeftClickBlock(LeftClickBlock event) {
        float progress = PlayerUtil.getCurBlockDamageMP() + BlockUtil.getHardness(event.getPos());
        if (progress >= 1) return;
        Relife1.INSTANCE.sendPacket(new CPacketPlayerDigging(
                CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(),
                mc.objectMouseOver.sideHit));
    }
}

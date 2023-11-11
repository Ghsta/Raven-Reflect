package zen.relife.module.impl.combat;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class FastBow extends Module {
    public FastBow() {
        super("FastBow", Keyboard.KEY_NONE, Category.COMBAT, false);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3.9087687765) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getAttackingEntity().getActiveHand()));
                mc.player.connection.sendPacket(new CPacketPlayer(mc.player.handleWaterMovement()));
                mc.player.stopActiveHand();
            }
        }
    }
}

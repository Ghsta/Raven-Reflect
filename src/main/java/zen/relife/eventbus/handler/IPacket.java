package zen.relife.eventbus.handler;

import net.minecraft.network.play.server.SPacketCollectItem;

public interface IPacket {

    boolean getClass(Class<SPacketCollectItem> sPacketCollectItemClass);
}

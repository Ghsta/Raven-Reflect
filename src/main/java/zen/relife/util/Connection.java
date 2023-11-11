package zen.relife.util;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import zen.relife.eventbus.EventEngine;

public class Connection
        extends ChannelDuplexHandler {
    private EventEngine eventEngine;

    public Connection(EventEngine eventEngine) {
    }


    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!this.eventEngine.onPacket(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!this.eventEngine.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public enum Side {
        IN,
        OUT
    }
}


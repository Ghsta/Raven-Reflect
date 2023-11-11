package zen.relife.eventbus.handler.impl;

import zen.relife.eventbus.handler.IPacket;

public class PacketEvent extends CancellableEvent {

    private final IPacket packet;

    public PacketEvent(IPacket packet) {
        this.packet = packet;
    }

    public final IPacket getPacket() {
        return this.packet;
    }

}


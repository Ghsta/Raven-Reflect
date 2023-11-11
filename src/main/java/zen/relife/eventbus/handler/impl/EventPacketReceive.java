package zen.relife.eventbus.handler.impl;

import net.minecraft.network.Packet;
import zen.relife.eventbus.Event;

public class EventPacketReceive implements Event {
    public Packet packet;

    public EventPacketReceive(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
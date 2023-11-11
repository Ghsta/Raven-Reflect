package zen.relife.eventbus.handler.impl;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import zen.relife.eventbus.handler.IPacket;

public class PacketEvents extends Event {
    private final Packet<?> packet;

    public PacketEvents(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    @Cancelable
    public static class PacketReceiveEvent extends PacketEvent {
        public PacketReceiveEvent(Packet<?> packet) {
            super((IPacket) packet);
        }
    }

    @Cancelable
    public static class PacketSendEvent extends PacketEvent {
        public PacketSendEvent(Packet<?> packet) {
            super((IPacket) packet);
        }
    }
}

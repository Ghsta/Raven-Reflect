package zen.relife.eventbus.eventapi.event;

import net.minecraft.network.Packet;
import zen.relife.eventbus.Event;

/**
 * @author: Thr1c0/s
 * @date: 2022/8/3 12:58
 */
public class EventPacket implements Event {

    private Packet packet;
    private PacketType packetType;

    public EventPacket(Packet packet, PacketType packetType) {
        this.packet = packet;
        this.packetType = packetType;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public enum PacketType {
        Client, Server
    }
}

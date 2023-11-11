package zen.relife.eventbus.handler.impl;

import javafx.event.EventType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import zen.relife.eventbus.Event;

public class EventPacket implements Event {
    public static boolean sendcancel;
    public static boolean recievecancel;
    public EntityLivingBase target;
    public Packet packet;
    private EventType type;
    private boolean cancelled;

    public EventPacket(EventType type, Object packet) {
        this.type = type;
        this.packet = (Packet) packet;
    }

    public static boolean isSendcancel() {
        return sendcancel;
    }

    public static void setSendcancel(boolean sendcancel) {
        EventPacket.sendcancel = sendcancel;
    }

    public static boolean isRecievecancel() {
        return recievecancel;
    }

    public static void setRecievecancel(boolean recievecancel) {
        EventPacket.recievecancel = recievecancel;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EntityLivingBase getTarget() {
        return this.target;
    }

    public void setTarget(EntityLivingBase target) {
        this.target = target;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}


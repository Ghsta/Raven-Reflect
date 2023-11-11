package zen.relife.eventbus.handler.impl;


import zen.relife.eventbus.Event;

public class EventPlayerUpdate implements Event {
    public float yaw;
    public float pitch;
    public double x;
    public double y;
    public double z;
    public int onGround;

    public EventPlayerUpdate(float yaw, float pitch, double x, double y, double z, int onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.x = x;
        this.z = z;
        this.onGround = onGround;
    }

    public Object getPacket() {
        return null;
    }

    public void isCancelled(boolean b) {
    }
}

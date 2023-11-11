package zen.relife.eventbus.eventapi.event;

import zen.relife.eventbus.eventapi.events.callables.EventCancellable;

public class EventPlayerPre extends EventCancellable {
    public static float YAW;
    public static float PITCH;
    public static float PREVYAW;
    public static float PREVPITCH;
    public static boolean SNEAKING;
    private final boolean isPre;
    private float yaw;
    private float pitch;
    private double x;
    private double y;
    private double z;
    private boolean onground;
    private boolean alwaysSend;
    private boolean sneaking;

    public EventPlayerPre(final double x, final double y, final double z, final float yaw, final float pitch, final boolean sneaking, final boolean ground) {
        this.isPre = true;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.x = x;
        this.z = z;
        this.onground = ground;
        this.sneaking = sneaking;
    }

    public EventPlayerPre() {
        EventPlayerPre.PREVYAW = EventPlayerPre.YAW;
        EventPlayerPre.PREVPITCH = EventPlayerPre.PITCH;
        EventPlayerPre.YAW = this.yaw;
        EventPlayerPre.PITCH = this.pitch;
        EventPlayerPre.SNEAKING = this.sneaking;
        this.isPre = false;
    }

    public boolean isPre() {
        return this.isPre;
    }

    public boolean isPost() {
        return !this.isPre;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    public double getX() {
        return this.x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public void setSneaking(final boolean sneaking) {
        this.sneaking = sneaking;
    }

    public boolean isOnground() {
        return this.onground;
    }

    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }

    public void setGround(final boolean ground) {
        this.onground = ground;
    }

    public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }

    public void setOnGround(final boolean onground) {
        this.onground = onground;
    }

    @Override
    public void cancel() {

    }
}

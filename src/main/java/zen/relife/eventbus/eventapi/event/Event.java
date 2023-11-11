package zen.relife.eventbus.eventapi.event;


public abstract class Event {

    private final Type type;
    private boolean cancelled;

    public Event(Type type) {
        this.type = type;
        this.cancelled = false;
    }

    public Type getType() {
        return type;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum Type {
        PRE, POST
    }


}
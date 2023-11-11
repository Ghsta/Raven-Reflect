package zen.relife.eventbus.eventapi.events.callables;


import zen.relife.eventbus.Event;
import zen.relife.eventbus.eventapi.events.Typed;

public abstract class EventTyped implements Event, Typed {
    private final byte type;

    protected EventTyped(final byte eventType) {
        this.type = eventType;
    }

    @Override
    public byte getType() {
        return this.type;
    }
}

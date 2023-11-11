package zen.relife.eventbus.eventapi.events.callables;

import zen.relife.eventbus.Cancellable;
import zen.relife.eventbus.Event;

public abstract class EventCancellable implements Event, Cancellable {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}

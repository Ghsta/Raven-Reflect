package zen.relife.eventbus.handler.impl;

import zen.relife.eventbus.Event;

public class CancellableEvent implements Event {
    private boolean isCancelled;

    public final boolean isCancelled() {
        return this.isCancelled;
    }

    public final void cancelEvent() {
        this.isCancelled = true;
    }
}

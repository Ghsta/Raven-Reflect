package zen.relife.eventbus.handler;

import zen.relife.eventbus.Event;

public interface Listener<T extends Event> {
    void call(T event);
}

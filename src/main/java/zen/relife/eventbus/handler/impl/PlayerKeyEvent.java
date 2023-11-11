package zen.relife.eventbus.handler.impl;

import zen.relife.eventbus.Event;

public final class PlayerKeyEvent implements Event {
    private final int key;

    public PlayerKeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

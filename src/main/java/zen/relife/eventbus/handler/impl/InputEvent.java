package zen.relife.eventbus.handler.impl;


import zen.relife.eventbus.Event;

public class InputEvent implements Event {
    public static class MouseInputEvent extends InputEvent {
    }

    public static class KeyInputEvent extends InputEvent {
    }
}

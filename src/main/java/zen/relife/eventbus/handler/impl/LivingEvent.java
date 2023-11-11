package zen.relife.eventbus.handler.impl;


import zen.relife.eventbus.Event;

public class LivingEvent implements Event {


    public <pr> LivingEvent(pr e) {
        super();
    }

    public static class LivingUpdateEvent extends LivingEvent {
        public <pr> LivingUpdateEvent(final pr e) {
            super(e);
        }
    }

}

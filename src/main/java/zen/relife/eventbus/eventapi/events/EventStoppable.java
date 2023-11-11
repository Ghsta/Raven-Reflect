package zen.relife.eventbus.eventapi.events;

public abstract class EventStoppable {
    private boolean stopped;

    public void stop() {
        this.stopped = true;
    }

    public boolean isStopped() {
        return this.stopped;
    }
}

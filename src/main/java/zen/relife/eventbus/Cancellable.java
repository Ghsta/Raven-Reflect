package zen.relife.eventbus;


public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

    void cancel();
}

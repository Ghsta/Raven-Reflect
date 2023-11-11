package zen.relife.eventbus.handler;

public interface IHandler {
    default boolean listening() {
        return true;
    }
}

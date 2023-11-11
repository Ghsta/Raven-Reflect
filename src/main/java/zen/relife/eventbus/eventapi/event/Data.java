package zen.relife.eventbus.eventapi.event;

import net.minecraftforge.fml.common.eventhandler.EventPriority;

import java.lang.reflect.Method;

public class Data {

    private final Object source;
    private final Method target;
    private final EventPriority priority;

    public Data(Object source, Method target, EventPriority priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }

    public Object getSource() {
        return source;
    }

    public Method getTarget() {
        return target;
    }

    public EventPriority getPriority() {
        return priority;
    }
}

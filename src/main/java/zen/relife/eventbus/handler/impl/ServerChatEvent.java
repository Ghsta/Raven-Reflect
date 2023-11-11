package zen.relife.eventbus.handler.impl;

import com.google.gson.annotations.Expose;
import zen.relife.eventbus.Event;

public final class ServerChatEvent implements Event {
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package org.iproute.guava.eventBus;

/**
 * Event
 *
 * @author zhuzhenjie
 * @since 5/16/2023
 */
public class Event {
    private String msg;

    public Event(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

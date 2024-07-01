package org.iproute.guava.eventBus;

import lombok.Getter;

/**
 * Event
 *
 * @author tech@intellij.io
 * @since 5/16/2023
 */
@Getter
public class Event {
    private final String msg;

    public Event(String msg) {
        this.msg = msg;
    }

}

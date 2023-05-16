package org.iproute.guava.eventBus;

import com.google.common.eventbus.EventBus;

import java.util.UUID;

/**
 * EventBusMain
 *
 * @author zhuzhenjie
 * @since 5/16/2023
 */
public class EventBusMain {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus("main");

        eventBus.register(new EventListener());

        eventBus.post(new Event(UUID.randomUUID().toString()));

    }
}

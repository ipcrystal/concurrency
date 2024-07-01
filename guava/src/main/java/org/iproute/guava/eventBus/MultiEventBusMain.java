package org.iproute.guava.eventBus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * MultiEventBus
 *
 * @author tech@intellij.io
 * @since 5/16/2023
 */
public class MultiEventBusMain {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus("MultiEventBusMain");

        eventBus.register(new MultiEventListener());

        eventBus.post(new EventA(UUID.randomUUID().toString()));
        eventBus.post(new EventB(UUID.randomUUID().toString()));
    }
}


@RequiredArgsConstructor
@Getter
class EventA {
    private final String msg;
}

@RequiredArgsConstructor
@Getter
class EventB {
    private final String msg;
}


class MultiEventListener {

    @Subscribe
    public void subscribeA(EventA eventA) {
        System.out.println("SubscribeA 接收到消息 :" + eventA.getMsg());
    }

    @Subscribe
    public void subscribeB(EventB eventB) {
        System.out.println("SubscribeB 接收到消息 :" + eventB.getMsg());

    }
}


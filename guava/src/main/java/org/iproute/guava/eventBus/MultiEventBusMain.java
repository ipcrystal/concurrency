package org.iproute.guava.eventBus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.UUID;

/**
 * MultiEventBus
 *
 * @author zhuzhenjie
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


class EventA {
    private String msg;

    public EventA(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

class EventB {
    private String msg;

    public EventB(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
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


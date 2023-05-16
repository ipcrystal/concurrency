package org.iproute.guava.eventBus;

import com.google.common.eventbus.Subscribe;

/**
 * EventListener
 *
 * @author zhuzhenjie
 * @since 5/16/2023
 */
public class EventListener {

    @Subscribe
    public void listen(Event event) {
        System.out.println("Subscribe 接收到消息 :" + event.getMsg());
    }
}

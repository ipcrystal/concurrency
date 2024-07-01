package org.iproute.block.waitnotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * WNMain
 *
 * @author tech@intellij.io
 * @since 5/4/2023
 */
public class WNMain {
    public static void main(String[] args) {

        final Object lock = new Object();

        Wait wait = new Wait(lock);

        Notify notify = new Notify(lock);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(wait);

        executor.execute(notify);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main down");

        executor.shutdown();

    }
}

/*
java的wait/notify的通知机制可以用来实现线程间通信。
wait表示线程的等待，调用该方法会导致线程阻塞，直至另一线程调用notify或notifyAll方法才可另其继续执行。
经典的生产者、消费者模式即是使用wait/notify机制得以完成
 */
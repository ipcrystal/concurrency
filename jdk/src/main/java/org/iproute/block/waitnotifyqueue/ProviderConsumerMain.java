package org.iproute.block.waitnotifyqueue;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * WNQMain
 *
 * @author tech@intellij.io
 * @since 5/4/2023
 */
@Slf4j
public class ProviderConsumerMain {

    public static void main(String[] args) {
        /*
        1秒生产1个，2秒消费一个，容器会满
         */
        // oneProvider_oneConsumer(5, 1, 2);

        /*
        2秒生产1个，1秒消费一个，容器会空
         */
        // oneProvider_oneConsumer(5, 2, 1);

        twoProvider_twoConsumer();
    }


    /**
     * Main 1.
     *
     * @param cap         容量
     * @param provideRate 生产速率 int秒 / 个
     * @param consumeRate 消费速率 int秒 / 个
     */
    static void oneProvider_oneConsumer(int cap, int provideRate, int consumeRate) {
        WNQueue queen = new WNQueue(cap);

        Consumer consumer = new Consumer(queen, "生产者");
        Provider provider = new Provider(queen, "消费者");

        SizePrint sizePrint = new SizePrint(queen);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        executor.scheduleAtFixedRate(sizePrint,
                0, 1, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(provider,
                0, provideRate, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(consumer,
                0, consumeRate, TimeUnit.SECONDS);
    }


    static void twoProvider_twoConsumer() {
        WNQueue queen = new WNQueue(10);

        Consumer consumer1 = new Consumer(queen, "consumer1");
        Consumer consumer2 = new Consumer(queen, "consumer2");

        Provider provider1 = new Provider(queen, "provider1");
        Provider provider2 = new Provider(queen, "provider2");

        SizePrint sizePrint = new SizePrint(queen);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        executor.scheduleAtFixedRate(sizePrint,
                0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(consumer1,
                0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(consumer2,
                0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(provider1,
                0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(provider2,
                0, 1, TimeUnit.SECONDS);

    }

    static class Consumer implements Runnable {

        private final WNQueue queen;
        private final String name;

        public Consumer(WNQueue queen, String name) {
            this.queen = queen;
            this.name = name;
        }

        @Override
        public void run() {
            int take = this.queen.take();
            // System.out.println("消费者 " + this.name + " take value : " + take);
            log.info("[消费者: {}] TAKE VALUE - {}", this.name, take);
        }
    }

    static class Provider implements Runnable {
        private final WNQueue queen;

        private final String name;

        public Provider(WNQueue queen, String name) {
            this.queen = queen;
            this.name = name;
        }

        @Override
        public void run() {
            int put = RandomUtils.nextInt(1000, 1_0000);
            // System.out.println("生产者 " + this.name + " put value : " + put);
            log.info("[生产者: {}] PUT VALUE - {}", this.name, put);
            queen.put(put);
        }
    }

    static class SizePrint implements Runnable {
        private final WNQueue queue;

        public SizePrint(WNQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            // System.out.println("block queen size is : " + queen.getSize());
            log.info("当前队列大小为 {}", queue.getSize());
        }
    }

}



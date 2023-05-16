package org.iproute.block.waitnotifyqueen;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * WNQMain
 *
 * @author zhuzhenjie
 * @since 5/4/2023
 */
public class WNQMain {

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
        WNQueen queen = new WNQueen(cap);

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
        WNQueen queen = new WNQueen(10);

        Consumer consumer1 = new Consumer(queen, "消费者1");
        Consumer consumer2 = new Consumer(queen, "消费者2");

        Provider provider1 = new Provider(queen, "生产者1");
        Provider provider2 = new Provider(queen, "生产者2");

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


}


class Consumer implements Runnable {

    private final WNQueen queen;
    private final String name;

    public Consumer(WNQueen queen, String name) {
        this.queen = queen;
        this.name = name;
    }

    @Override
    public void run() {
        int take = this.queen.take();
        System.out.println("消费者 " + this.name + " take value : " + take);
    }
}

class Provider implements Runnable {
    private final WNQueen queen;
    private final Random random;

    private final String name;

    public Provider(WNQueen queen, String name) {
        this.queen = queen;
        this.random = new Random();
        this.name = name;
    }

    @Override
    public void run() {
        int put = random.nextInt();
        System.out.println("生产者 " + this.name + " put value : " + put);
        queen.put(put);
    }
}

class SizePrint implements Runnable {
    private final WNQueen queen;

    public SizePrint(WNQueen queen) {
        this.queen = queen;
    }

    @Override
    public void run() {
        System.out.println("block queen size is : " + queen.getSize());
    }
}
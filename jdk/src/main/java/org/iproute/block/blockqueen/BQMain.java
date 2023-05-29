package org.iproute.block.blockqueen;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * BQMain
 *
 * @author zhuzhenjie
 * @since 5/3/2023
 */
public class BQMain {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();

        Provider provider = new Provider(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);
        SizePrint sizePrint = new SizePrint(blockingQueue);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // 延迟 0 秒， 每1秒生产一个元素
        executor.scheduleAtFixedRate(provider,
                0, 1, TimeUnit.SECONDS);

        // 延迟 0 秒， 每2秒消费一个元素
        executor.scheduleAtFixedRate(consumer,
                0, 2, TimeUnit.SECONDS);


        // 延迟 0 秒， 每1秒打印队列元素的个数
        executor.scheduleAtFixedRate(sizePrint,
                0, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(20);

        executor.shutdown();
    }
}

/*

1. ArrayBlockingQueue：ArrayBlockingQueue 是一个有界的阻塞队列,其内部实现是将对象放到一个数组里。有界也就意味着，它不能够存储无限多数量的元素。它有一个同一时间能够存储元素数量的上限。
你可以在对其初始化的时候设定这个上限，但之后就无法对这个上限进行修改了(译者注：因为它是基于数组实现的，也就具有数组的特性：一旦初始化，大小就无法修改)

2. DelayQueue：DelayQueue 对元素进行持有直到一个特定的延迟到期。注入其中的元素必须实现 java.util.concurrent.Delayed 接口。
LinkedBlockingQueue：LinkedBlockingQueue 内部以一个链式结构(链接节点)对其元素进行存储。如果需要的话，这一链式结构可以选择一个上限。如果没有定义上限，将使用 Integer.MAX_VALUE 作为上限

3. PriorityBlockingQueue：PriorityBlockingQueue 是一个无界的并发队列。它使用了和类 java.util.PriorityQueue 一样的排序规则。
你无法向这个队列中插入 null 值。所有插入到 PriorityBlockingQueue 的元素必须实现 java.lang.Comparable 接口。因此该队列中元素的排序就取决于你自己的 Comparable 实现。

4. SynchronousQueue：SynchronousQueue 是一个特殊的队列，它的内部同时只能够容纳单个元素。
如果该队列已有一元素的话，试图向队列中插入一个新元素的线程将会阻塞，直到另一个线程将该元素从队列中抽走。同样，如果该队列为空，试图向队列中抽取一个元素的线程将会阻塞，直到另一个线程向队列中插入了一条新的元素。
据此，把这个类称作一个队列显然是夸大其词了。它更多像是一个汇合点

 */
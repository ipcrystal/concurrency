package org.iproute.test.block;

import org.iproute.block.blockqueue.Consumer;
import org.iproute.block.blockqueue.Provider;
import org.iproute.block.blockqueue.SizePrint;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * BlockQueueTest
 *
 * @author zhuzhenjie
 * @since 2023/8/6
 */
public class BlockQueueTest {

    @Test
    public void test() throws InterruptedException {
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

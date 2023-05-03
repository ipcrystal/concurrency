package org.iproute.blockqueen;

import java.util.concurrent.BlockingQueue;

/**
 * Consumer
 *
 * @author zhuzhenjie
 * @since 5/3/2023
 */
public class Consumer implements Runnable {

    private final BlockingQueue<Integer> blockingQueue;

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            Integer take = blockingQueue.take();
            System.out.println("take value is :" + take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

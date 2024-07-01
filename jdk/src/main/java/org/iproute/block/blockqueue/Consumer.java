package org.iproute.block.blockqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

/**
 * Consumer
 *
 * @author tech@intellij.io
 * @since 5/3/2023
 */
@Slf4j
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
            log.error("", e);
        }
    }
}

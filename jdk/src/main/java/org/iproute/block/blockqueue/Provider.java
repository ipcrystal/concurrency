package org.iproute.block.blockqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Provider
 *
 * @author tech@intellij.io
 * @since 5/3/2023
 */
@Slf4j
public class Provider implements Runnable {

    private final BlockingQueue<Integer> blockingQueue;
    private final Random random;


    public Provider(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.random = new Random();
    }

    @Override
    public void run() {
        int value = random.nextInt();
        try {
            System.out.println("put value is :" + value);
            blockingQueue.put(value);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }
}

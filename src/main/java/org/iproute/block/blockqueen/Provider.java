package org.iproute.block.blockqueen;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Provider
 *
 * @author zhuzhenjie
 * @since 5/3/2023
 */
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
            e.printStackTrace();
        }
    }
}

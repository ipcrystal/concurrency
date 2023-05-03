package org.iproute.blockqueen;

import java.util.concurrent.BlockingQueue;

/**
 * LogPrint
 *
 * @author zhuzhenjie
 * @since 5/3/2023
 */
public class SizePrint implements Runnable {
    private final BlockingQueue<Integer> blockingQueue;

    public SizePrint(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int size = blockingQueue.size();
        System.out.println("blockingQueue size is " + size);
    }
}

package org.iproute.threadpool.debug;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.iproute.threadpool.debug.MainConst.core;
import static org.iproute.threadpool.debug.MainConst.max;
import static org.iproute.threadpool.debug.MainConst.queue;

/**
 * MainQueue
 *
 * @author tech@intellij.io
 */
public class Main2Queue {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                core, max, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queue)
        );

        for (int i = 0; i < (core + 1); i++) {
            executor.execute(() -> {
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }
}

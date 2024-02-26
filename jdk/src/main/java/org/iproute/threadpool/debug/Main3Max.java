package org.iproute.threadpool.debug;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.iproute.threadpool.debug.MainConst.core;
import static org.iproute.threadpool.debug.MainConst.max;
import static org.iproute.threadpool.debug.MainConst.queue;

/**
 * MainMax
 *
 * @author zhuzhenjie
 */
public class Main3Max {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                core, max, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queue)
        );

        for (int i = 0; i < (core + queue + 1); i++) {
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

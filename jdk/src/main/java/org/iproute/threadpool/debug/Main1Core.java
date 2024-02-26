package org.iproute.threadpool.debug;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.iproute.threadpool.debug.MainConst.core;
import static org.iproute.threadpool.debug.MainConst.max;
import static org.iproute.threadpool.debug.MainConst.queue;

/**
 * MainCore
 *
 * @author zhuzhenjie
 */
public class Main1Core {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                core, max, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queue)
        );

        for (int i = 0; i < core; i++) {
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

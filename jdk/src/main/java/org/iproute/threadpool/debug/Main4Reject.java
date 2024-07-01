package org.iproute.threadpool.debug;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.iproute.threadpool.debug.MainConst.core;
import static org.iproute.threadpool.debug.MainConst.max;
import static org.iproute.threadpool.debug.MainConst.queue;

/**
 * Main4Reject
 *
 * @author tech@intellij.io
 */
public class Main4Reject {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                core, max, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queue),
                new RejectHandler()
        );
        for (int i = 0; i < (core + queue + (max - core) + 1); i++) {
            executor.execute(() -> {
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Slf4j
    public static class RejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("do reject");
        }
    }
}

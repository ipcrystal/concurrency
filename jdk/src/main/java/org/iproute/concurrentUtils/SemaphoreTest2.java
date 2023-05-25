package org.iproute.concurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * SemaphoreTest2
 *
 * @author winterfell
 * @since 2022/2/15
 */
@SuppressWarnings("all")
public class SemaphoreTest2 {

    private static final int THREAD_COUNT = 10;

    private static ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + ": sava data");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("wait for finished");
        executor.shutdown();
    }

}

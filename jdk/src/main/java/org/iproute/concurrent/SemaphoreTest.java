package org.iproute.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SemaphoreTest
 *
 * @author winterfell
 * @since 2022/2/15
 */
public class SemaphoreTest {

    private static final int THREAD_COUNT = 100;

    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    private static final Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + ": sava data");
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();

    }

}

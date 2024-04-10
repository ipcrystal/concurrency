package org.iproute.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * FutureMain
 *
 * @author zhuzhenjie
 * @since 5/5/2023
 */
public class FutureMain {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });

        try {
            // 线程阻塞
            Integer integer = future.get();
            System.out.println(integer);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


        executor.shutdown();

    }

}

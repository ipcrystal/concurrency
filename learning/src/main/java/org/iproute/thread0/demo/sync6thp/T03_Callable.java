package org.iproute.thread0.demo.sync6thp;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author tech@intellij.io
 */
public class T03_Callable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<String> callable = () -> {
            return "hello world";
        };

        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<String> stringFuture = service.submit(callable);

        System.out.println(stringFuture.get());

        service.shutdown();

    }

}

package org.iproute.futureTask;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * FutureTaskMain
 *
 * @author tech@intellij.io
 * @since 5/5/2023
 */
public class FutureTaskMain {

    public static void main(String[] args) throws Exception {

        FutureTask<String> futureTask = new FutureTask<>(
                () -> {
                    Thread.sleep(3_000);
                    System.out.println("hello world");
                    return "hello world";
                }
        );
        new Thread(futureTask).start();

        System.out.println(futureTask.get(1, TimeUnit.SECONDS));

    }
}

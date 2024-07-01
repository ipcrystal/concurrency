package org.iproute.guava.listenableFuture;

import java.util.concurrent.Callable;

/**
 * LFCallable
 *
 * @author tech@intellij.io
 * @since 5/15/2023
 */
public class LFCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1_000);
        return 1000;
    }
}

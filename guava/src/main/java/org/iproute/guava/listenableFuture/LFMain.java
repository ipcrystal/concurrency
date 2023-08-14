package org.iproute.guava.listenableFuture;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * LFMain
 *
 * @author zhuzhenjie
 * @since 5/15/2023
 */
@Slf4j
public class LFMain {

    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();

        ListenableFuture<Integer> listenableFuture = executorService.submit(new LFCallable());

        // type 1
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("get listenable future's result " + listenableFuture.get());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }, executorService);

        // type 2
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("", t);
            }
        }, executorService);


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }

}

package org.iproute.guava.listenableFuture2;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * LFMain2
 *
 * @author tech@intellij.io
 * @since 2023/6/12
 */
@Slf4j
public class LFMain2 {

    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        ListenableFuture<Integer> lfSuccess = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call execute..");
                TimeUnit.SECONDS.sleep(1);
                return 7;
            }
        });

        ListenableFuture<Integer> lfFailure = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(1);
                throw new RuntimeException("测试失败");
            }
        });


        Futures.addCallback(lfSuccess, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("result = " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.err.println(t.getMessage());
            }
        }, executorService);


        Futures.addCallback(lfFailure, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("result = " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                // System.out.println("onFailure");
                // System.err.println(t.getMessage());
                log.info("onFailure");

                log.error("", t);
            }
        }, executorService);

    }

}

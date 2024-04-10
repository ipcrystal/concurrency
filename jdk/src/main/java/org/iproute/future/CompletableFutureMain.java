package org.iproute.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureMain
 *
 * @author zhuzhenjie
 * @since 5/5/2023
 */
public class CompletableFutureMain {
    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("电饭煲开始做饭");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "白米饭";
        }).thenAccept(result -> {
            System.out.println("开始吃" + result);
        });


        System.out.println("我去做点其他事儿");
        future.join();

    }
}

/*

https://juejin.cn/post/6844904195162636295

join 合并结果，等待
get 合并等待结果，可以增加超时时间;get和join区别，join只会抛出unchecked异常，get会返回具体的异常
getNow 如果结果计算完成或者异常了，则返回结果或异常；否则，返回valueIfAbsent的值
isCancelled
isCompletedExceptionally
isDone

 */
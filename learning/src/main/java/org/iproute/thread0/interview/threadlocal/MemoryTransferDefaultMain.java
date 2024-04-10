package org.iproute.thread0.interview.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MemoryTransferDefaultMain
 *
 * @author zhuzhenjie
 */
public class MemoryTransferDefaultMain {

    static final ThreadLocal<String> localStr = new ThreadLocal<>();

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        localStr.set("新年快乐");

        executorService.submit(() -> {
            System.out.println("我是线程1： " + localStr.get());
        });

        executorService.submit(() -> {
            System.out.println("我是线程2： " + localStr.get());
        });
    }


}

package org.iproute.concurrentUtils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger 线程间交换数据
 *
 * @author winterfell
 * @since 2022/2/15
 */
@SuppressWarnings("all")
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        pool.execute(()->{
            String a = "银行流水A";
            try {
                exgr.exchange(a);
            } catch (Exception e) {
            }
        });


        pool.execute(()->{
            String b = "银行流水B";

            try {
                String a_ = exgr.exchange("B");
                System.out.println("A 和 B 的数据是否一致: " + a_.equals(b) + ",\r\nA录入的数据是: " + a_ + "\r\nB录入的数据是: " + b);

            } catch (InterruptedException e) {
            }


        });
    }


}

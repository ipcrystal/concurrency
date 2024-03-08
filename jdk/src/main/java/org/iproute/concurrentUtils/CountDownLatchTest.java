package org.iproute.concurrentUtils;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchTest
 *
 * @author winterfell
 * @since 2022/2/14
 */
public class CountDownLatchTest {

    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(1);
            c.countDown();
            System.out.println(2);
            c.countDown();
        }).start();

        c.await();

        System.out.println(3);
    }
}

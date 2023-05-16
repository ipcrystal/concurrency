package org.iproute.conutils;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchTest
 *
 * @author winterfell
 * @since 2022/2/14
 */
@SuppressWarnings("all")
public class CountDownLatchTest {

    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

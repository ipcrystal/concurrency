package org.iproute.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierTest2
 *
 * @author tech@intellij.io
 * @since 2022/2/14
 */
public class CyclicBarrierTest2 {

    static CyclicBarrier c = new CyclicBarrier(2, new A());

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        new Thread(() -> {
            System.out.println(1);
            try {
                c.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        System.out.println(2);
        c.await();
    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(3);
        }
    }

}

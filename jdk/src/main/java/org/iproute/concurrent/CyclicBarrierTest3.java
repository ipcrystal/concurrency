package org.iproute.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierTest3
 *
 * @author tech@intellij.io
 * @since 2022/2/15
 */
public class CyclicBarrierTest3 {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();
        thread.interrupt();

        try {
            c.await();
        } catch (Exception e) {
            System.out.println(c.isBroken());
        }

    }
}

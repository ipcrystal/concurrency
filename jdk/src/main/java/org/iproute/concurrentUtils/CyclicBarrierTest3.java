package org.iproute.concurrentUtils;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierTest3
 *
 * @author winterfell
 * @since 2022/2/15
 */
@SuppressWarnings("all")
public class CyclicBarrierTest3 {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {
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

package org.iproute.concurrentUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarriedTest
 *
 * @author winterfell
 * @since 2022/2/14
 */
public class CyclicBarrierTest {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(1);
        }).start();

        // 主线程await
        c.await();
        System.out.println(2);
    }
}

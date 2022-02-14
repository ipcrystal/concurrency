package org.iproute.conutils;

/**
 * JoinCountDownLatch
 *
 * @author winterfell
 * @since 2022/2/14
 */
@SuppressWarnings("all")
public class JoinCountDownLatch {

    public static void main(String[] args) throws InterruptedException {

        Thread parser1 = new Thread(() -> {
            System.out.println("parser1 finished");
        });

        Thread parser2 = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("parser2 finished");
        });


        parser1.start();
        parser2.start();

        parser1.join();
        parser2.join();

        System.out.println("all parser finished");

    }

}

package org.iproute.join;

/**
 * JoinMain
 *
 * @author tech@intellij.io
 * @since 5/25/2023
 */
public class JoinMain {

    public static void main(String[] args) throws InterruptedException {

        Runnable a = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                threadMsg(i);
            }
        };

        Thread thread = new Thread(a, "A");

        thread.start();
        thread.join(2_000);

        threadMsg("this is main thread");
    }

    static void threadMsg(Object msg) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] msg = " + msg);
    }

}

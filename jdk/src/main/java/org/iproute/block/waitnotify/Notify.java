package org.iproute.block.waitnotify;

/**
 * Notify
 *
 * @author zhuzhenjie
 * @since 5/3/2023
 */
public class Notify implements Runnable {
    private final Object lock;

    public Notify(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {

            System.out.println("start notify");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            lock.notify();

            System.out.println("after notify");
        }
    }
}

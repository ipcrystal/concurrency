package org.iproute.block.waitnotify;

/**
 * Wait
 *
 * @author tech@intellij.io
 * @since 5/3/2023
 */
public class Wait implements Runnable {

    private final Object lock;

    public Wait(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                System.out.println("wait ...");
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("after wait , do something");
        }
    }
}

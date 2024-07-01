package org.iproute.interrupt;

/**
 * InterruptMain
 *
 * @author tech@intellij.io
 * @since 5/5/2023
 */
public class InterruptMain {

    public static void main(String[] args) {

        Thread thread = Thread.currentThread();

        thread.interrupt();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("hello world");

    }
}

package org.iproute;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Main
 *
 * @author winterfell
 * @since 2022/1/13
 */
public class Main {
    static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            lock.lock();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }, "hello").start();

        Thread.sleep(1000);
        System.out.println("hello world");

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }, "word").start();

        Thread.sleep(5000);
        lock.lock();
        try {
            Thread.sleep(1000000);
        } finally {
            lock.unlock();
        }
    }

}

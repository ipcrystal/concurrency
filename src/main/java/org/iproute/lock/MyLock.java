package org.iproute.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author winterfell
 */
public class MyLock {

    static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        new Thread(() -> {
            sayHello();
        }, "t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            sayHello();
        }, "t2").start();
    }

    static void sayHello() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

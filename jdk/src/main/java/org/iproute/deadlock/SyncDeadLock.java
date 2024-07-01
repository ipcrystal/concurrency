package org.iproute.deadlock;

import lombok.extern.slf4j.Slf4j;

/**
 * SyncDeadLock
 *
 * @author tech@intellij.io
 * @since 5/25/2023
 */
@Slf4j
public class SyncDeadLock {
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public static void main(String[] args) {
        new SyncDeadLock().deadlock();
    }


    void deadlock() {

        new Thread(() -> {
            synchronized (lockA) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    log.error("", e);
                }

                log.info("线程A尝试获取对象B的锁");
                synchronized (lockB) {
                    log.info("do something");
                }
            }
        }, "ThreadA").start();

        new Thread(() -> {
            synchronized (lockB) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("线程B尝试获取对象A的锁");
                synchronized (lockA) {
                    log.info("do something");
                }
            }
        }, "ThreadB").start();

    }
}

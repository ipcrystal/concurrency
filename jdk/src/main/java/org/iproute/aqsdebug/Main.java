package org.iproute.aqsdebug;

import java.util.concurrent.TimeUnit;

/**
 * Main
 *
 * @author tech@intellij.io
 */
public class Main {
    public static void main(String[] args) {
        try {
            fairLockMain();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fairLockMain() throws InterruptedException {
        RtLock lock = new RtLock(false);
        new Thread(new FairRunnable(lock, 10L, true, 3), "thread-1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(new FairRunnable(lock, 5L, false, 0), "thread-2").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(new FairRunnable(lock, 5L, false, 0), "thread-3").start();

        TimeUnit.SECONDS.sleep(3);
        lock.lock();

        try {
            System.out.println("main");
        } finally {
            lock.unlock();
        }
    }


    public static class FairRunnable implements Runnable {
        private final RtLock lock;
        private final Long cost;
        // 是否模拟重入
        private final boolean reLock;

        // 重入次数
        private final int reLockTime;

        public FairRunnable(RtLock lock, Long cost, boolean reLock, int reLockTime) {
            this.lock = lock;
            this.cost = cost;
            this.reLock = reLock;
            this.reLockTime = reLockTime;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (reLock) {
                    reLockMock(reLockTime);
                    for (int i = 0; i < reLockTime; i++) {
                        // 释放锁
                        lock.unlock();
                    }
                }

                TimeUnit.SECONDS.sleep(cost);
                Consolas.log("%s 秒后执行完任务", cost);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }

        private void reLockMock(int time) {
            if (time > 0) {
                Consolas.log("RtLock锁 重入的第 %d 次", reLockTime - time + 1);
                lock.lock();
                reLockMock(time - 1);
            }
        }

    }

}

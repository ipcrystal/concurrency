package org.iproute.aqsdebug;

import java.util.concurrent.TimeUnit;

/**
 * Main
 *
 * @author zhuzhenjie
 */
public class Main {
    public static void main(String[] args) {
        fairLockMain();
    }

    public static void fairLockMain() {
        RtLock lock = new RtLock(true);
        new Thread(new FairRunnable(lock, 2L, true, 10), "thread-1st").start();
        new Thread(new FairRunnable(lock, 2L, false, 0), "thread-2nd").start();
        new Thread(new FairRunnable(lock, 2L, false, 0), "thread-3rd").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
                Logger.log("%s 秒后执行完任务", cost);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }

        private void reLockMock(int time) {
            if (time > 0) {
                Logger.log("RtLock锁 重入的第 %d 次", reLockTime - time + 1);
                lock.lock();
                reLockMock(time - 1);
            }
        }

    }

}

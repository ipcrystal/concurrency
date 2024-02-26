package org.iproute.threadpool.extend;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * PausableThreadPoolExecutor
 *
 * @author zhuzhenjie
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor {

    private boolean isPaused;
    private final ReentrantLock pauseLock = new ReentrantLock();
    private final Condition unPaused = pauseLock.newCondition();

    public PausableThreadPoolExecutor(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            // 暂停
            while (isPaused) {
                unPaused.await();
            }
        } catch (Exception e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        PausableThreadPoolExecutor executor = new PausableThreadPoolExecutor(
                5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10)
        );

        executor.pause();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executor.execute(() -> {
                System.out.println(" i = " + finalI);
            });
        }

        TimeUnit.SECONDS.sleep(3);

        executor.resume();

    }

}

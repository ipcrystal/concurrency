package org.iproute.thread0.interview.loop;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AlternatePrinting
 * <p>
 * 两个线程轮流打印 1 2 3 4 5....
 *
 * @author zhuzhenjie
 */
@Slf4j
public class AlternatePrinting {

    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(1);

        int max = 10;

        new Thread(new PrintRunnable(i, max), "a").start();
        new Thread(new PrintRunnable(i, max), "b").start();

    }

    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    static class PrintRunnable implements Runnable {
        private final AtomicInteger number;
        private final int max;


        public PrintRunnable(AtomicInteger number, int max) {
            this.number = number;
            this.max = max;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                while (number.get() < max) {
                    try {
                        log.info("num is {}", number.getAndIncrement());

                        condition.signal();

                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

}

package org.iproute.concurrent;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier模拟银行流水
 *
 * @author winterfell
 * @since 2022/2/14
 */
@SuppressWarnings("all")
public class BankWaterService implements Runnable {

    /**
     * this 作为最后的统计计算
     */
    private final CyclicBarrier c = new CyclicBarrier(4, this);

    private Executor executor = Executors.newFixedThreadPool(4);

    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                // 模拟4个线程计算流水
                try {
                    Thread.sleep(RandomUtils.nextLong(0, 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                int value = RandomUtils.nextInt(0, 5);
                System.out.println(Thread.currentThread().getName() + " = " + value);
                sheetBankWaterCount.put(Thread.currentThread().getName(), value);

                try {
                    c.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });

        }
    }

    public static void main(String[] args) {
        new BankWaterService().count();
    }

    @Override
    public void run() {
        int result = 0;
        for (int value : sheetBankWaterCount.values()) {
            result += value;
        }
        System.out.println("result = " + result);
    }
}

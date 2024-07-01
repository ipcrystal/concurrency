package org.iproute.threadpool;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DefaultThreadPool
 *
 * @param <Job> the type parameter
 * @author tech@intellij.io
 * @since 2022 /2/15
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    /**
     * 线程池最大限制数
     */
    private static final int MAX_WORKER_NUMBERS = 10;

    /**
     * 默认线程池的数量
     */
    private static final int DEFAULT_WORKER_NUMBERS = 5;

    /**
     * 线程池最小的数量
     */
    private static final int MIN_WORKER_NUMBERS = 1;

    /**
     * 这是一个工作列表，会向里面插入工作
     */
    private final LinkedList<Job> jobs = new LinkedList<>();

    /**
     * 工作者线程
     */
    private final LinkedList<Worker> workers = new LinkedList<>();

    /**
     * 工作者线程的数量
     */
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    /**
     * 线程编号的生成
     */
    private final AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : (Math.max(num, MIN_WORKER_NUMBERS));
        initializeWorkers(workerNum);
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorker(int num) {
        synchronized (jobs) {
            // 限制新增的Worker不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num > this.workerNum) {
                throw new IllegalArgumentException("beyond wordNum");
            }

            // 按照给定的数量停止worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }

            this.workerNum -= count;
        }
    }

    @Override
    public int getJobsSize() {
        return jobs.size();
    }

    @SuppressWarnings("all")
    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    class Worker implements Runnable {
        /**
         * 是否工作
         */
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    // 取出第一个job
                    job = jobs.remove();
                }
                if (job != null) {
                    try {
                        // 真正执行的地方
                        job.run();
                    } catch (Exception e) {
                        // 忽略job执行的Exception
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}

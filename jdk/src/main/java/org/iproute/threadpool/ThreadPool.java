package org.iproute.threadpool;

/**
 * ThreadPool
 *
 * @param <Job> the type parameter
 * @author tech@intellij.io
 * @since 2022 /2/15
 */
public interface ThreadPool<Job extends Runnable> {

    /**
     * 执行一个job
     *
     * @param job the job
     */
    void execute(Job job);

    /**
     * 关闭线程池.
     */
    void shutdown();

    /**
     * 增加工作者线程
     *
     * @param num the num
     */
    void addWorker(int num);

    /**
     * 减少工作者线程
     *
     * @param num the num
     */
    void removeWorker(int num);

    /**
     * 得到正在等待执行的任务数量
     *
     * @return the jobs size
     */
    int getJobsSize();
}

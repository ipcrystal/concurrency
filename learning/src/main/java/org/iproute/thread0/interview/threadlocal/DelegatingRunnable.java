package org.iproute.thread0.interview.threadlocal;

/**
 * DelegatingRunnable
 *
 * @author zhuzhenjie
 */
public class DelegatingRunnable implements Runnable {

    private Runnable runnable;

    @Override
    public void run() {


        runnable.run();
    }
}

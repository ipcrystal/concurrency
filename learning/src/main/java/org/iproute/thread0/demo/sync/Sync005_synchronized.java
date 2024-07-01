package org.iproute.thread0.demo.sync;

/**
 * 分析一下这个程序的输出
 *
 * @author tech@intellij.io
 */
public class Sync005_synchronized implements Runnable {

    private int count = 10;

    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        Sync005_synchronized test = new Sync005_synchronized();
        for (int i = 0; i < 5; i++) {
            new Thread(test, "THREAD" + i).start();
        }
    }
}

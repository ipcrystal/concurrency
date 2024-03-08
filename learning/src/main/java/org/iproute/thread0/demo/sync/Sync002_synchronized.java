package org.iproute.thread0.demo.sync;

/**
 * @author winterfell
 */
public class Sync002_synchronized {

    private int count = 10;

    private void m() {

        synchronized (this) { // 任何线程要执行下面的代码 必须要拿到this的锁
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

}

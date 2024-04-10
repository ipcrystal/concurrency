package org.iproute.thread0.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * 细粒度的锁 效率高于 粗粒度的锁
 *
 * @author winterfell
 */
public class Test016 {

    int count = 0;

    synchronized void m1() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        count++;

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    void m2() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this) {
            count++;
        }

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}

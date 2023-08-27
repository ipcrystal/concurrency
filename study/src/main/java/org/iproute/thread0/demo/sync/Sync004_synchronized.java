package org.iproute.thread0.demo.sync;

/**
 * synchronized 如果用在静态方法上，相当与锁定当前对象的 Class对象
 *
 * @author winterfell
 */
public class Sync004_synchronized {

    private static int count = 10;

    public synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        synchronized (Sync004_synchronized.class) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    // m() 和 mm() 方法等价

}

package org.iproute.interrupt;

/**
 * InterruptMain
 *
 * @author zhuzhenjie
 * @since 5/5/2023
 */
public class InterruptMain2 {

    public static void main(String[] args) throws InterruptedException {
        IThread t = new IThread();

        t.start();

        // 主线程1秒设置其他线程的 interrupt 标识
        Thread.sleep(1_000);
        t.interrupt();

        Thread.sleep(3_000);
    }
}


class IThread extends Thread {

    @Override
    public void run() {
        System.out.println("第一次 " + this.isInterrupted());
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            System.out.println("第二次 " + this.isInterrupted());
            e.printStackTrace();
        }

        System.out.println("第三次 " + this.isInterrupted());
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            System.out.println("第四次 " + this.isInterrupted());
            e.printStackTrace();
        }
    }
}
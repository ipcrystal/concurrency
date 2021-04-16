package org.iproute;

/**
 * Main 查看字节码
 *
 * @author winterfell
 * @since 2021/4/16
 */
public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        new Thread(main::test).start();

        new Thread(main::test2).start();
    }

    public void test() {
        synchronized (this) {
            System.out.println("hello");
        }
    }

    public synchronized void test2() {
        System.out.println("hello");
    }
}

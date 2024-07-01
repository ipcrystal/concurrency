package org.iproute.thread0.demo.sync4.test023;

/**
 * @author tech@intellij.io
 */
public class Singleton {

    private static Singleton instance = null;

    private static final Object lock = new Object();

    private Singleton() {
    }

    public static Singleton getInstance() {
        /*
        if (null == instance) {
            synchronized (Singleton.class) {
                if (null == instance) {
                    instance = new Singleton();
                }
            }
        }
         */

        while (null == instance) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }

        return instance;
    }

    public static void main(String[] args) {

        Singleton singleton = Singleton.getInstance();

    }


}

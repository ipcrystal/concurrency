package org.iproute.tutorial;

/**
 * DeadLock
 *
 * @author tech@intellij.io
 * @since 5/25/2023
 */
public class DeadLock {

    static class Friend {
        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public synchronized void bow(Friend bower) {
            System.out.printf("%s: %s has bowed to me! %n", this.name, bower.name);
            bower.bowBack(this);
        }

        public synchronized void bowBack(Friend bower) {
            System.out.printf("%s: %s has bowed back to me! %n", this.name, bower.name);
        }
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");

        final Friend gaston = new Friend("Gaston");

        new Thread(() -> {
            alphonse.bow(gaston);
        }).start();

        new Thread(() -> {
            gaston.bow(alphonse);
        }).start();
    }

}

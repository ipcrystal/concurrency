package org.iproute.http;

import java.util.concurrent.TimeUnit;

/**
 * PoolTest
 *
 * @author winterfell
 * @since 2022/2/17
 */
public class PoolTest {


    public static void main(String[] args) {

        ThreadPool<Say> pool = new DefaultThreadPool<>();

        for (int i = 0; i < 10; i++) {
            pool.execute(new Say("hello " + i));
        }
    }

    static class Say implements Runnable {
        private final String word;

        public Say(String word) {
            this.word = word;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(word);
        }
    }
}
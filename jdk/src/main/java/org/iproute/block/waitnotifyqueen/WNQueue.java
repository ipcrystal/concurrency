package org.iproute.block.waitnotifyqueen;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 利用wait / notifyAll 实现生产者消费者安全
 *
 * @author zhuzhenjie
 * @since 5/4/2023
 */
@Slf4j
public class WNQueue {

    private final Object lock = new Object();
    private final LinkedList<Integer> container;
    private final int cap;
    private volatile int size;

    public int getSize() {
        return this.size;
    }

    public WNQueue(int cap) {
        this.size = 0;
        this.cap = cap;
        this.container = new LinkedList<>();
    }

    public void put(Integer value) {
        synchronized (lock) {
            // if (this.size >= this.cap) {
            //     System.out.println("容器已满,等待消费");
            //     try {
            //         lock.wait(); // 等待消费，释放锁了
            //     } catch (InterruptedException e) {
            //         throw new RuntimeException(e);
            //     }
            // }

            // todo: 思考为什么用的是while 而不是if 重要：双重检查的思想
            // 这是一个双重的判断，如果用if，如果有两个生产者，一个消费者，
            // 其中两个生产者都wait()了，那么，消费者只消费了一个元素
            // 两个生产者都走 if 之后的语句，那么生产出来的元素就会超过规定的 capacity
            while (this.size == this.cap) {
                // System.out.println("容器已满,等待消费");
                log.info("容器已满,等待消费");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            this.container.add(value);
            this.size++;

            // System.out.println("加入元素,通知消费");
            log.info("加入元素,通知消费");
            lock.notifyAll();
        }
    }


    public int take() {
        int take;
        synchronized (lock) {
            // if (this.size == 0) {
            //     System.out.println("容器已空,等待生产");
            //     try {
            //         lock.wait(); // 等待生产
            //     } catch (InterruptedException e) {
            //         throw new RuntimeException(e);
            //     }
            // }

            // todo: 思考为什么用的是while 而不是if
            while (this.size == 0) {
                // System.out.println("容器已空,等待生产");
                log.info("容器已空,等待生产");
                try {
                    lock.wait(); // 等待生产
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            take = this.container.removeFirst();
            this.size--;
            // System.out.println("消费元素，通知生产");
            log.info("消费元素，通知生产");
            lock.notifyAll();
            return take;
        }
    }

}

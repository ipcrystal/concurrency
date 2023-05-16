package org.iproute.guava.stopWatch;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * StopWatchMain
 *
 * @author zhuzhenjie
 * @since 5/16/2023
 */
public class StopWatchMain {
    public static void main(String[] args) throws InterruptedException {

        Stopwatch stopwatch = Stopwatch.createStarted();

        Thread.sleep(1_000);

        stopwatch.stop();

        System.out.println("elapsed milliseconds is : " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}

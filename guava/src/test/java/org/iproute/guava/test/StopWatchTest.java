package org.iproute.guava.test;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * StopWatchTest
 *
 * @author tech@intellij.io
 * @since 2023/8/4
 */
@Slf4j
public class StopWatchTest {

    @Test
    public void testCost() {

        Stopwatch stopwatch = Stopwatch.createStarted();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            // log.error("", e);
        }

        stopwatch.stop();

        log.info("elapsed milliseconds is : {}", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

}

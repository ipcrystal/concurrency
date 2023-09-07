package org.iproute.aqsdebug;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LoggerConst
 *
 * @author zhuzhenjie
 */
public abstract class Logger {

    private final static DateFormat DF = new SimpleDateFormat("HH:mm:ss.SSS");

    public static void log(String format, Object... args) {
        log(Thread.currentThread(), format, args);
    }

    public static void log(Thread thread, String format, Object... args) {
        String curThreadName = Thread.currentThread().getName();
        System.out.printf("%s [ %18s ] : %s%n",
                DF.format(new Date()),
                curThreadName,
                String.format(format, args));
    }
}

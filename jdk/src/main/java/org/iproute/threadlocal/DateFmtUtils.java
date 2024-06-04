package org.iproute.threadlocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DateFmtUtils
 *
 * @author devops@kubectl.net
 */
public class DateFmtUtils {

    private static final Map<String, DateFormat> fmtMap = new ConcurrentHashMap<>();

    public static DateFmtResult parse(String dateString, String fmtString) {
        try {
            DateFormat fmt;
            if (fmtMap.containsKey(fmtString)) {
                fmt = fmtMap.get(fmtString);
            } else {
                fmt = new SimpleDateFormat(fmtString);
                fmtMap.put(fmtString, fmt);
            }
            return DateFmtResult.builder()
                    .valid(true)
                    .date(fmt.parse(dateString))
                    .build();
        } catch (Exception e) {
            return DateFmtResult.builder()
                    .valid(false)
                    .invalidMsg(e.getMessage() + "|dateString=" + dateString + "|fmtString=" + fmtString)
                    .date(new Date(0))
                    .build();
        }
    }

}

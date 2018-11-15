package com.loanbroker.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static Date daysFromEpochToDate(long days) {
        long miliFromEpoch = Instant.ofEpochMilli(TimeUnit.DAYS.toMillis(days)).toEpochMilli();
        return new Date(Instant.EPOCH.toEpochMilli() + Instant.ofEpochMilli(miliFromEpoch).toEpochMilli());
    }

    public static String dateToString(Date date) {
        return dateFormat().format(date);
    }

    public static DateFormat dateFormat() {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss.S zzz");
        return format;
    }
}

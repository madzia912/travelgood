package travelgood.utils;

import java.util.Calendar;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class DateUtils {

    private DateUtils() {
    }

    public static long getTimestamp(int year, int month, int day) {
        return getTimestamp(year, month, day, 0, 0);
    }

    public static long getTimestamp(int year, int month, int day, int hour, int minute) {
        return getTimestamp(year, month, day, hour, minute, 0);
    }

    public static long getTimestamp(int year, int month, int day, int hour, int minute, int seconds) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, seconds);
        return c.getTimeInMillis();
    }
}

package travelgood.utils;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.Calendar;
import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class DateUtils {

    private DateUtils() {
    }
    
    public static XMLGregorianCalendar getXmlGregorianCalendar(int year, int month, int day) {
        return getXmlGregorianCalendar(year, month, day, 0, 0);
    }

    public static XMLGregorianCalendar getXmlGregorianCalendar(int year, int month, int day, int hour, int minute) {
        return getXmlGregorianCalendar(year, month, day, hour, minute, 0);
    }

    public static XMLGregorianCalendar getXmlGregorianCalendar(int year, int month, int day, int hour, int minute, int seconds) {
        return XMLGregorianCalendarImpl.createDateTime(year, month, day, hour, minute, seconds);
    }
    
    public static Date getDate(int year, int month, int day) {
        return getDate(year, month, day, 0, 0);
    }

    public static Date getDate(int year, int month, int day, int hour, int minute) {
        return getDate(year, month, day, hour, minute, 0);
    }
    
    public static Date getDate(int year, int month, int day, int hour, int minute, int seconds) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, seconds);
        return c.getTime();
    }
}

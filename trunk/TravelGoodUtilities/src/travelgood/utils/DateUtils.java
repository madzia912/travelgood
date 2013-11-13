package travelgood.utils;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class DateUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static XMLGregorianCalendar toXmlGregorianCalendar(String date) throws ParseException {
        return toXmlGregorianCalendar(stringToDate(date));
    }

    private DateUtils() {
    }
    
    public static String dateToString(Date date) {
        return sdf.format(date);
    }

    public static Date stringToDate(String source) throws ParseException {
        try {
            return sdf.parse(source);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public static XMLGregorianCalendar toXmlGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Bad date: " + date, ex);
        }
    }

    public static Date toDate(XMLGregorianCalendar xmlgc) {
        if (xmlgc == null) {
            return null;
        }
        return xmlgc.toGregorianCalendar().getTime();
    }

    public static XMLGregorianCalendar getXmlGregorianCalendar(int year, int month, int day) {
        return getXmlGregorianCalendar(year, month, day, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
    }

    public static XMLGregorianCalendar getXmlGregorianCalendar(int year, int month, int day, int hour, int minute) {
        return getXmlGregorianCalendar(year, month, day, hour, minute, DatatypeConstants.FIELD_UNDEFINED);
    }

    public static XMLGregorianCalendar getXmlGregorianCalendar(int year, int month, int day, int hour, int minute, int seconds) {
        return XMLGregorianCalendarImpl.createDateTime(year, month, day, hour, minute, seconds, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
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

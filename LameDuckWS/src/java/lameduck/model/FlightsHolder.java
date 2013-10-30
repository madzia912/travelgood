package lameduck.model;

import dk.dtu.travelgood.commons.FlightType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import travelgood.utils.DateUtils;

/**
 * Holds all available flights and reservations.
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class FlightsHolder {

    private static FlightsHolder INSTANCE = new FlightsHolder();
    private List<FlightType> flights = new ArrayList<FlightType>();
    private Map<String, FlightType> reservations = new HashMap<String, FlightType>();

    private FlightsHolder() {
        init();
    }

    public static FlightsHolder getInstance() {
        return INSTANCE;
    }

    public List<FlightType> getFlights() {
        return flights;
    }

    public FlightType getFlightByBookingNumber(String nr) {
        for (FlightType ft : flights) {
            if (StringUtils.equals(ft.getBookingNumber(), nr)) {
                return ft;
            }
        }
        return null;
    }

    public Map<String, FlightType> getReservations() {
        return reservations;
    }

    private void init() {
        /*
         * These sample flights are used for tests.
         * If you modify them, also modify test!
         */
        FlightType ft = new FlightType();
        ft.setId("flight1");
        ft.setBookingNumber("bookingNr1");
        ft.setFrom("ABC");
        ft.setTo("CBA");
        ft.setCarrier("SAS");
        ft.setLiftOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));
        ft.setLandingDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));
        ft.setPrice(100);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight2");
        ft.setBookingNumber("bookingNr2");
        ft.setFrom("ASD");
        ft.setTo("DSA");
        ft.setCarrier("SAS");
        ft.setLiftOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 12));
        ft.setLandingDate(DateUtils.getXmlGregorianCalendar(2012, 10, 12));
        ft.setPrice(200);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight3");
        ft.setBookingNumber("bookingNr3");
        ft.setFrom("QWE");
        ft.setTo("EWQ");
        ft.setCarrier("SAS");
        ft.setLiftOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 14));
        ft.setLandingDate(DateUtils.getXmlGregorianCalendar(2012, 10, 15));
        ft.setPrice(120);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight4");
        ft.setBookingNumber("bookingNr4");
        ft.setFrom("ERT");
        ft.setTo("TRE");
        ft.setCarrier("SAS");
        ft.setLiftOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 16));
        ft.setLandingDate(DateUtils.getXmlGregorianCalendar(2012, 10, 17));
        ft.setPrice(250);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight5");
        ft.setBookingNumber("bookingNr5");
        ft.setFrom("POI");
        ft.setTo("IOP");
        ft.setCarrier("SAS");
        ft.setLiftOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));
        ft.setLandingDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));
        ft.setPrice(100000);
        flights.add(ft);
        /*
         * End of sample flights used for tests.
         */
    }
}

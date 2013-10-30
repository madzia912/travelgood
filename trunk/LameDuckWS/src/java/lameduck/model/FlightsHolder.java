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
    private Map<String, ReservationData> reservations = new HashMap<String, ReservationData>();

    private FlightsHolder() {
        init();
    }

    public static FlightsHolder getInstance() {
        return INSTANCE;
    }

    public List<FlightType> getFlights() {
        return flights;
    }

    public FlightType getFlightById(String id) {
        for (FlightType ft : flights) {
            if (StringUtils.equals(ft.getId(), id)) {
                return ft;
            }
        }
        return null;
    }

    public Map<String, ReservationData> getReservations() {
        return reservations;
    }

    private void init() {
        /*
         * These sample flights are used for tests.
         * If you modify them, also modify test!
         */
        FlightType ft = new FlightType();
        ft.setId("flight1");
        ft.setFrom("ABC");
        ft.setTo("CBA");
        ft.setPlacesLeft(100);
        ft.setTimestamp(DateUtils.getTimestamp(2013, 10, 10, 12, 00));
        ft.setPrice(100);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight2");
        ft.setFrom("ASD");
        ft.setTo("DSA");
        ft.setPlacesLeft(100);
        ft.setTimestamp(DateUtils.getTimestamp(2013, 10, 10, 15, 00));
        ft.setPrice(200);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight3");
        ft.setFrom("QWE");
        ft.setTo("EWQ");
        ft.setPlacesLeft(200);
        ft.setTimestamp(1370612954);
        ft.setPrice(120);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight4");
        ft.setFrom("ERT");
        ft.setTo("TRE");
        ft.setPlacesLeft(100);
        ft.setTimestamp(DateUtils.getTimestamp(2013, 10, 12, 16, 00));
        ft.setPrice(250);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight5");
        ft.setFrom("POI");
        ft.setTo("IOP");
        ft.setPlacesLeft(0);
        ft.setTimestamp(DateUtils.getTimestamp(2013, 10, 15, 18, 00));
        ft.setPrice(555);
        flights.add(ft);
        /*
         * End of sample flights used for tests.
         */
    }
}

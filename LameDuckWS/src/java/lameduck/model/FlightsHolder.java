package lameduck.model;

import dk.dtu.travelgood.commons.FlightType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, FlightType> getReservations() {
        return reservations;
    }

    private void init() {
        FlightType ft = new FlightType();
        ft.setId("flight1");
        ft.setFrom("ABC");
        ft.setTo("BCD");
        ft.setPlacesLeft(100);
        ft.setTimestamp(10000000);
        ft.setPrice(100);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight2");
        ft.setFrom("ASD");
        ft.setTo("SDF");
        ft.setPlacesLeft(100);
        ft.setTimestamp(20000000);
        ft.setPrice(200);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight3");
        ft.setFrom("QWE");
        ft.setTo("WER");
        ft.setPlacesLeft(200);
        ft.setTimestamp(15000000);
        ft.setPrice(120);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight4");
        ft.setFrom("ERT");
        ft.setTo("RTY");
        ft.setPlacesLeft(100);
        ft.setTimestamp(30000000);
        ft.setPrice(250);
        flights.add(ft);

        ft = new FlightType();
        ft.setId("flight5");
        ft.setFrom("POI");
        ft.setTo("IOK");
        ft.setPlacesLeft(0);
        ft.setTimestamp(40000000);
        ft.setPrice(555);
        flights.add(ft);
    }
}

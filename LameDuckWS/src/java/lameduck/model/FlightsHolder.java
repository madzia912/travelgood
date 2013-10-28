package lameduck.model;

import java.util.ArrayList;
import java.util.List;
import travelgood.utils.model.Flight;

/**
 * Holds all available flights.
 *
 * @author Bartosz Cichecki
 */
public class FlightsHolder {

    private List<Flight> flights = new ArrayList<Flight>();
    private static FlightsHolder INSTANCE = new FlightsHolder();

    private FlightsHolder() {
    }

    public FlightsHolder getInstance() {
        return INSTANCE;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}

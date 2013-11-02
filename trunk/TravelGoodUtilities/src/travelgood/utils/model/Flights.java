package travelgood.utils.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bartosz Grzegorz Cichecki
 */
public class Flights {

    protected List<Flight> flights;

    public List<Flight> getFlights() {
        if (flights == null) {
            flights = new ArrayList<Flight>();
        }
        return this.flights;
    }
}

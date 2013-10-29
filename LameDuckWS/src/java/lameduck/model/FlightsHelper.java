package lameduck.model;

import dk.dtu.lameduck.BookFlightFault_Exception;
import dk.dtu.travelgood.commons.FlightsType;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class FlightsHelper {

    public FlightsType getFlights(String from, String to, long dateFrom, long dateTo) {
        FlightsType ft = new FlightsType();
        return ft;
    }

    public String bookFlight(String flightId) throws BookFlightFault_Exception {
        return "OK";
    }

    public String cancelFlight(String reservationId) {
        return "OK";
    }
    
}

package lameduck.model;

import dk.dtu.travelgood.commons.FlightType;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class ReservationData {
    
    private FlightType flight;
    private int placesReserved;

    public ReservationData(FlightType flight, int placesReserved) {
        this.flight = flight;
        this.placesReserved = placesReserved;
    }
    
    public FlightType getFlight() {
        return flight;
    }

    public void setFlight(FlightType flight) {
        this.flight = flight;
    }

    public int getPlacesReserved() {
        return placesReserved;
    }

    public void setPlacesReserved(int placesReserved) {
        this.placesReserved = placesReserved;
    }
    
}

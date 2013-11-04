package travelgood.utils.model.rest;

import javax.xml.bind.annotation.XmlRootElement;
import travelgood.utils.model.Flights;

/**
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
public class GetFlightsResponse extends AbstractListResponse {

    protected Flights flights;

    public Flights getFlights() {
        return flights;
    }

    public void setFlights(Flights flights) {
        this.flights = flights;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.flights != null ? this.flights.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GetFlightsResponse other = (GetFlightsResponse) obj;
        if (this.flights != other.flights && (this.flights == null || !this.flights.equals(other.flights))) {
            return false;
        }
        return true;
    }
}

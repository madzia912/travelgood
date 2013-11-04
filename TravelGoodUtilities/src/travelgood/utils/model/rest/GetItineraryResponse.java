package travelgood.utils.model.rest;

import javax.xml.bind.annotation.XmlRootElement;
import travelgood.utils.model.Itinerary;

/**
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
public class GetItineraryResponse extends AbstractListResponse<String> {

    protected Itinerary itinerary;

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.itinerary != null ? this.itinerary.hashCode() : 0);
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
        final GetItineraryResponse other = (GetItineraryResponse) obj;
        if (this.itinerary != other.itinerary && (this.itinerary == null || !this.itinerary.equals(other.itinerary))) {
            return false;
        }
        return true;
    }
}

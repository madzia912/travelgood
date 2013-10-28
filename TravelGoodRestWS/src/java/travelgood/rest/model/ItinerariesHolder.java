package travelgood.rest.model;

import java.util.ArrayList;
import java.util.List;
import travelgood.utils.model.Itinerary;

/**
 * Holds all available itineraries.
 *
 * @author Bartosz Cichecki
 */
public class ItinerariesHolder {

    private List<Itinerary> itineraries = new ArrayList<Itinerary>();
    private static ItinerariesHolder INSTANCE = new ItinerariesHolder();

    private ItinerariesHolder() {
    }

    public ItinerariesHolder getInstance() {
        return INSTANCE;
    }

    public List<Itinerary> getFlights() {
        return itineraries;
    }
}

package travelgood.model;

import java.util.HashMap;
import java.util.Map;
import travelgood.utils.model.Itinerary;

/**
 *
 * @author Magdalena Furman
 */
public class ItineraryHolder {

    private Map<String, Itinerary> temporaryItineraries = new HashMap<String, Itinerary>();
    private Map<String, Itinerary> bookedItineraries = new HashMap<String, Itinerary>();
    private static ItineraryHolder INSTANCE = new ItineraryHolder();

    private ItineraryHolder() {
    }

    public ItineraryHolder getInstance() {
        return INSTANCE;
    }

    public Map<String, Itinerary> getTemporaryItineraries() {
        return temporaryItineraries;
    }

    public Map<String, Itinerary> getBookedItineraries() {
        return bookedItineraries;
    }
}

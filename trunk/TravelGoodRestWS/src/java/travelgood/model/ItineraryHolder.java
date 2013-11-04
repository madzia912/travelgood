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

    public static ItineraryHolder getInstance() {
        return INSTANCE;
    }

    public Map<String, Itinerary> getTemporaryItineraries() {
        return temporaryItineraries;
    }

    public void addTemporaryItineraries(Itinerary itinerary) {
        temporaryItineraries.put(itinerary.getBookingNumber(), itinerary);
    }

    public Map<String, Itinerary> getBookedItineraries() {
        return bookedItineraries;
    }

    public void addBookedItineraries(Itinerary itinerary) {
        bookedItineraries.put(itinerary.getBookingNumber(), itinerary);
    }
}

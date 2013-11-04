package travelgood.model;

import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Itinerary;
import travelgood.utils.model.ItineraryState;

/**
 *
 * @author Magdalena Furman
 */
public class ItineraryHelper {

    public Itinerary createItinerary(String userId) {
        // TODO Implement
        Itinerary itinerary = new Itinerary();
        itinerary.setBookingNumber("bookingNumber");
        return itinerary;
    }

    public Itinerary getItinerary(String bookingNumber) throws ItineraryException {
        // TODO Implement
        return new Itinerary();
    }

    public Itinerary bookItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException {
        // TODO Implement
        Itinerary itinerary = new Itinerary();
        itinerary.setItineraryState(ItineraryState.BOOKED);
        return itinerary;
    }

    public Itinerary cancelItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException {
        // TODO Implement
        Itinerary itinerary = new Itinerary();
        itinerary.setItineraryState(ItineraryState.CANCELLED);
        return itinerary;
    }

    public boolean addFlight(String bookingNumber, String flightBookingNumber) throws ItineraryException {
        // TODO Implement
        return true;
    }

    public boolean addHotel(String bookingNumber, String hotelBookingNumber) throws ItineraryException {
        // TODO Implement
        return true;
    }

    public boolean deleteFlight(String bookingNumber, String flightBookingNumber) throws ItineraryException {
        // TODO Implement
        return true;
    }

    public boolean deleteHotel(String bookingNumber, String hotelBookingNumber) throws ItineraryException {
        // TODO Implement
        return true;
    }
}

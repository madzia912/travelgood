package travelgood.model;

import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Itinerary;

/**
 *
 * @author Magdalena Furman
 */
public class ItineraryHelper {

    public String createItinerary(String userId) {
        // TODO Implement
        return "bookingNumber";
    }

    public Itinerary getItinerary(String bookingNumber) throws ItineraryException {
        // TODO Implement
        return new Itinerary();
    }

    public boolean bookItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException {
        // TODO Implement
        return true;
    }

    public boolean cancelItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException {
        // TODO Implement
        return true;
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

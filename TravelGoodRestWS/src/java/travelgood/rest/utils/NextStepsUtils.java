package travelgood.rest.utils;

import java.util.ArrayList;
import java.util.Collection;
import travelgood.utils.model.Flights;
import travelgood.utils.model.Hotels;
import travelgood.utils.model.Itinerary;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class NextStepsUtils {

    public static Collection<String> getNextStepsForItinerary(Itinerary itinerary) {
        return new ArrayList<String>();
    }

    public static Collection<String> getNextStepsForGetFlights(String itineraryBookingNumber, Flights flights) {
        return new ArrayList<String>();
    }
    
    public static Collection<String> getNextStepsForGetHotels(String itineraryBookingNumber, Hotels hotels) {
        return new ArrayList<String>();
    }

    public static Collection<String> getNextStepsForBookFlight(String itineraryBookingNumber, String flightBookingNumber) {
        return new ArrayList<String>();
    }
    
    public static Collection<String> getNextStepsForBookHotel(String itineraryBookingNumber, String flightBookingNumber) {
        return new ArrayList<String>();
    }

    public static Collection<String> getNextStepsForDeleteFlight(String itineraryBookingNumber, String flightBookingNumber) {
        return new ArrayList<String>();
    }
    
    public static Collection<String> getNextStepsForDeleteHotel(String itineraryBookingNumber, String flightBookingNumber) {
        return new ArrayList<String>();
    }
}

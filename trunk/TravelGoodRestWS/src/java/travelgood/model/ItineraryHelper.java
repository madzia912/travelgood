package travelgood.model;

import java.util.List;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Address;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Flight;
import travelgood.utils.model.Hotel;
import travelgood.utils.model.Itinerary;
import travelgood.utils.model.ItineraryState;
import travelgood.utils.model.PaymentStatus;

/**
 *
 * @author Magdalena Furman
 */
public class ItineraryHelper {

    private lameduck.client.LameDuckPortType lameDuckPort = new lameduck.client.LameDuckService().getLameDuckPort();
    private niceview.client.NiceViewPortType niceViewPort = new niceview.client.NiceViewService().getNiceViewPortTypeBindingPort();

    private lameduck.client.LameDuckPortType getLameDuckPort() {
        return lameDuckPort;
    }

    private niceview.client.NiceViewPortType getNiceViewPort() {
        return niceViewPort;
    }

    public Itinerary createItinerary(String userId) {
        Itinerary itinerary = new Itinerary();
        itinerary.setBookingNumber(String.valueOf(System.nanoTime())); // generating the random ID
        itinerary.setItineraryState(ItineraryState.IN_PROGRESS);
        itinerary.setPaymentStatus(PaymentStatus.PENDING);

        ItineraryHolder.getInstance().addTemporaryItineraries(itinerary);
        return itinerary;
    }

    public Itinerary getItinerary(String bookingNumber) throws ItineraryException {
        if (ItineraryHolder.getInstance().getTemporaryItineraries().containsKey(bookingNumber)) {
            return ItineraryHolder.getInstance().getTemporaryItineraries().get(bookingNumber);
        }

        if (ItineraryHolder.getInstance().getBookedItineraries().containsKey(bookingNumber)) {
            return ItineraryHolder.getInstance().getBookedItineraries().get(bookingNumber);
        }

        return null;
    }

    public Itinerary bookItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException, lameduck.client.BookFlightFault_Exception, niceview.client.BookHotelFault_Exception {
        Itinerary itinerary = getItinerary(bookingNumber);
        if (itinerary == null) {
            ItineraryException fault = new ItineraryException();
            fault.setReason("Itinerary does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new ItineraryException("Could not book itinerary " + bookingNumber + ": does not exist.", fault);
        }

        //Book all the assigned flights and hotels
        List<Flight> flights = itinerary.getFlights();
        List<Hotel> hotels = itinerary.getHotels();

        lameduck.client.CreditCardType lameDuckCreditCard = new lameduck.client.CreditCardType();
        lameDuckCreditCard.setExpMonth(creditCard.getExpMonth());
        lameDuckCreditCard.setExpYear(creditCard.getExpYear());
        lameDuckCreditCard.setName(creditCard.getName());
        lameDuckCreditCard.setNumber(creditCard.getNumber());

        niceview.client.CreditCardType niceViewCreditCard = new niceview.client.CreditCardType();
        niceViewCreditCard.setExpMonth(creditCard.getExpMonth());
        niceViewCreditCard.setExpYear(creditCard.getExpYear());
        niceViewCreditCard.setName(creditCard.getName());
        niceViewCreditCard.setNumber(creditCard.getNumber());

        for (Flight f : flights) {
            lameduck.client.BookFlightRequest bookRequest = new lameduck.client.BookFlightRequest();
            bookRequest.setBookingNumber(f.getBookingNumber());
            bookRequest.setCreditCard(lameDuckCreditCard);
            getLameDuckPort().bookFlight(bookRequest);
        }

        for (Hotel h : hotels) {
            niceview.client.BookHotelRequest bookRequest = new niceview.client.BookHotelRequest();
            bookRequest.setBookingNumber(h.getBookingNumber());
            bookRequest.setCreditCard(niceViewCreditCard);
            getNiceViewPort().bookHotel(bookRequest);
        }

        itinerary.setItineraryState(ItineraryState.BOOKED);

        // Copy from temporary to booked itineraries
        ItineraryHolder.getInstance().getTemporaryItineraries().remove(bookingNumber);
        ItineraryHolder.getInstance().getBookedItineraries().put(itinerary.getBookingNumber(), itinerary);

        return itinerary;
    }

    public Itinerary cancelItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException, lameduck.client.CancelReservationFault_Exception, niceview.client.CancelHotelFault_Exception {
        Itinerary itinerary = getItinerary(bookingNumber);
        if (itinerary == null) {
            ItineraryException fault = new ItineraryException();
            fault.setReason("Itinerary does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new ItineraryException("Could not cancel itinerary " + bookingNumber + ": does not exist.", fault);
        }

        //Book all the assigned flights and hotels
        List<Flight> flights = itinerary.getFlights();
        List<Hotel> hotels = itinerary.getHotels();

        lameduck.client.CreditCardType lameDuckCreditCard = new lameduck.client.CreditCardType();
        lameDuckCreditCard.setExpMonth(creditCard.getExpMonth());
        lameDuckCreditCard.setExpYear(creditCard.getExpYear());
        lameDuckCreditCard.setName(creditCard.getName());
        lameDuckCreditCard.setNumber(creditCard.getNumber());

        niceview.client.CreditCardType niceViewCreditCard = new niceview.client.CreditCardType();
        niceViewCreditCard.setExpMonth(creditCard.getExpMonth());
        niceViewCreditCard.setExpYear(creditCard.getExpYear());
        niceViewCreditCard.setName(creditCard.getName());
        niceViewCreditCard.setNumber(creditCard.getNumber());

        for (Flight f : flights) {
            lameduck.client.CancelReservationRequest cancelRequest = new lameduck.client.CancelReservationRequest();
            cancelRequest.setBookingNumber(f.getBookingNumber());
            cancelRequest.setCreditCard(lameDuckCreditCard);
            cancelRequest.setPrice(f.getPrice());
            getLameDuckPort().cancelReservation(cancelRequest);
        }

        for (Hotel h : hotels) {
            niceview.client.CancelHotelRequest cancelRequest = new niceview.client.CancelHotelRequest();
            cancelRequest.setBookingNumber(h.getBookingNumber());
            getNiceViewPort().cancelHotel(cancelRequest);
        }

        itinerary.setItineraryState(ItineraryState.CANCELLED);
        return itinerary;
    }

    public boolean addFlight(String bookingNumber, String flightBookingNumber) throws ItineraryException {
        Itinerary itinerary = getItinerary(bookingNumber);
        if (itinerary == null) {
            ItineraryException fault = new ItineraryException();
            fault.setReason("Itinerary does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new ItineraryException("Could not cancel itinerary " + bookingNumber + ": does not exist.", fault);
        }

        lameduck.client.GetFlightsRequest getFlightRequest = new lameduck.client.GetFlightsRequest();
        getFlightRequest.setBookingNumber(flightBookingNumber);
        lameduck.client.GetFlightsResponse getFlightResponse = lameDuckPort.getFlights(getFlightRequest);
        if (getFlightResponse.getFlights().getFlight().size() > 0) {
            lameduck.client.FlightType flightType = getFlightResponse.getFlights().getFlight().get(0);
            Flight flight = new Flight(flightType.getId(), flightType.getBookingNumber(), flightType.getCarrier(),
                    flightType.getFrom(), flightType.getTo(), DateUtils.toDate(flightType.getLiftOffDate()),
                    DateUtils.toDate(flightType.getLandingDate()), flightType.getPrice());
            itinerary.getFlights().add(flight);
            return true;
        }

        return false;
    }

    public boolean addHotel(String bookingNumber, String hotelBookingNumber) throws ItineraryException {
        Itinerary itinerary = getItinerary(bookingNumber);
        if (itinerary == null) {
            ItineraryException fault = new ItineraryException();
            fault.setReason("Itinerary does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new ItineraryException("Could not cancel itinerary " + bookingNumber + ": does not exist.", fault);
        }

        niceview.client.GetHotelsRequest getHotelRequest = new niceview.client.GetHotelsRequest();
        getHotelRequest.setBookingNumber(hotelBookingNumber);
        niceview.client.GetHotelsResponse getHotelResponse = niceViewPort.getHotels(getHotelRequest);
        if (getHotelResponse.getHotels().getHotel().size() > 0) {
            niceview.client.HotelType hotelType = new niceview.client.HotelType();
            niceview.client.AddressType addressType = new niceview.client.AddressType();
            Address address = new Address(addressType.getCity(), addressType.getStreet(), addressType.getZipCode());
            Hotel hotel = new Hotel(hotelType.getId(), hotelType.getBookingNumber(), hotelType.getName(), address, hotelType.getProvider(), hotelType.getPrice(), hotelType.isCreditCardGuarantee());
            itinerary.getHotels().add(hotel);
            return true;
        }

        return false;
    }

    public boolean deleteFlight(String bookingNumber, String flightBookingNumber) throws ItineraryException {
        Itinerary itinerary = getItinerary(bookingNumber);
        if (itinerary == null) {
            ItineraryException fault = new ItineraryException();
            fault.setReason("Itinerary does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new ItineraryException("Could not cancel itinerary " + bookingNumber + ": does not exist.", fault);
        }
        lameduck.client.GetFlightsRequest getFlightRequest = new lameduck.client.GetFlightsRequest();
        getFlightRequest.setBookingNumber(flightBookingNumber);
        lameduck.client.GetFlightsResponse getFlightResponse = lameDuckPort.getFlights(getFlightRequest);
        if (getFlightResponse.getFlights().getFlight().size() > 0) {
            lameduck.client.FlightType flightType = getFlightResponse.getFlights().getFlight().get(0);
            Flight flight = new Flight(flightType.getId(), flightType.getBookingNumber(), flightType.getCarrier(),
                    flightType.getFrom(), flightType.getTo(), DateUtils.toDate(flightType.getLiftOffDate()),
                    DateUtils.toDate(flightType.getLandingDate()), flightType.getPrice());
            if (itinerary.getFlights().contains(flight)) {
                itinerary.getFlights().remove(flight);
                return true;
            } else {
                ItineraryException fault = new ItineraryException();
                fault.setReason("Flight was not assigned to this itinerary does not exist.");
                fault.setBookingNumber(bookingNumber);
                throw new ItineraryException("Could not delete flight " + flightBookingNumber + ": does not exist in itinerary " + bookingNumber, fault);
            }
        }
        return false;
    }

    public boolean deleteHotel(String bookingNumber, String hotelBookingNumber) throws ItineraryException {
        Itinerary itinerary = getItinerary(bookingNumber);
        if (itinerary == null) {
            ItineraryException fault = new ItineraryException();
            fault.setReason("Itinerary does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new ItineraryException("Could not cancel itinerary " + bookingNumber + ": does not exist.", fault);
        }
        niceview.client.GetHotelsRequest getHotelRequest = new niceview.client.GetHotelsRequest();
        getHotelRequest.setBookingNumber(hotelBookingNumber);
        niceview.client.GetHotelsResponse getHotelResponse = niceViewPort.getHotels(getHotelRequest);
        if (getHotelResponse.getHotels().getHotel().size() > 0) {
            niceview.client.HotelType hotelType = new niceview.client.HotelType();
            niceview.client.AddressType addressType = new niceview.client.AddressType();
            Address address = new Address(addressType.getCity(), addressType.getStreet(), addressType.getZipCode());
            Hotel hotel = new Hotel(hotelType.getId(), hotelType.getBookingNumber(), hotelType.getName(), address, hotelType.getProvider(), hotelType.getPrice(), hotelType.isCreditCardGuarantee());
            if (itinerary.getHotels().contains(hotel)) {
                itinerary.getHotels().remove(hotel);
                return true;
            } else {
                ItineraryException fault = new ItineraryException();
                fault.setReason("Hotel was not assigned to this itinerary does not exist.");
                fault.setBookingNumber(bookingNumber);
                throw new ItineraryException("Could not delete hotel " + hotelBookingNumber + ": does not exist in itinerary " + bookingNumber, fault);
            }
        }
        return false;
    }
}

package travelgood.model;

import java.util.ArrayList;
import java.util.List;
import niceview.client.AddressType;
import niceview.client.HotelType;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Address;
import travelgood.utils.model.BookingState;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Flight;
import travelgood.utils.model.Hotel;
import travelgood.utils.model.Itinerary;
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
        itinerary.setBookingState(BookingState.IN_PROGRESS);
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

        ItineraryException fault = new ItineraryException();
        fault.setReason("Itinerary does not exist.");
        fault.setBookingNumber(bookingNumber);
        throw new ItineraryException("Could not book itinerary " + bookingNumber + ": does not exist.", fault);
    }

    public Itinerary bookItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException, lameduck.client.BookFlightFault_Exception, niceview.client.BookHotelFault_Exception, lameduck.client.CancelReservationFault_Exception, niceview.client.CancelHotelFault_Exception {
        Itinerary itinerary = getItinerary(bookingNumber);

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

        List<Object> bookedFlightsAndHotels = new ArrayList<Object>();

        try {

            for (Flight f : flights) {
                lameduck.client.BookFlightRequest bookRequest = new lameduck.client.BookFlightRequest();
                bookRequest.setBookingNumber(f.getBookingNumber());
                bookRequest.setCreditCard(lameDuckCreditCard);
                getLameDuckPort().bookFlight(bookRequest);
                f.setBookingState(BookingState.BOOKED);
                bookedFlightsAndHotels.add(f);
            }

            for (Hotel h : hotels) {
                niceview.client.BookHotelRequest bookRequest = new niceview.client.BookHotelRequest();
                bookRequest.setBookingNumber(h.getBookingNumber());
                bookRequest.setCreditCard(niceViewCreditCard);
                getNiceViewPort().bookHotel(bookRequest);
                h.setBookingState(BookingState.BOOKED);
                bookedFlightsAndHotels.add(h);
            }

            itinerary.setBookingState(BookingState.BOOKED);
            itinerary.setPaymentStatus(PaymentStatus.SUCCESS);

            // Copy from temporary to booked itineraries
            ItineraryHolder.getInstance().getTemporaryItineraries().remove(bookingNumber);
            ItineraryHolder.getInstance().getBookedItineraries().put(itinerary.getBookingNumber(), itinerary);

            return itinerary;

        } catch (Exception ex) { //Rollback all the booked items
            for (Object obj : bookedFlightsAndHotels) {
                if (obj instanceof Flight) {
                    Flight f = (Flight) obj;
                    lameduck.client.CancelReservationRequest cancelRequest = new lameduck.client.CancelReservationRequest();
                    cancelRequest.setBookingNumber(f.getBookingNumber());
                    cancelRequest.setCreditCard(lameDuckCreditCard);
                    cancelRequest.setPrice(f.getPrice());
                    getLameDuckPort().cancelReservation(cancelRequest);
                    f.setBookingState(BookingState.CANCELLED);
                } else {
                    Hotel h = (Hotel) obj;
                    niceview.client.CancelHotelRequest cancelRequest = new niceview.client.CancelHotelRequest();
                    cancelRequest.setBookingNumber(h.getBookingNumber());
                    getNiceViewPort().cancelHotel(cancelRequest);
                    h.setBookingState(BookingState.CANCELLED);
                }
            }
            ItineraryException fault = new ItineraryException();
            fault.setReason("Booking of one of the assigned flights or hotels failed.");
            fault.setBookingNumber(bookingNumber);
            //throw new ItineraryException("Could not book itinerary " + bookingNumber + ": does not exist.", fault);
            throw new ItineraryException(ex);
        }
    }

    public Itinerary cancelItinerary(String bookingNumber, CreditCard creditCard) throws ItineraryException, lameduck.client.CancelReservationFault_Exception, niceview.client.CancelHotelFault_Exception {
        Itinerary itinerary = getItinerary(bookingNumber);

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
            if (f.getBookingState().equals(BookingState.BOOKED)) {
                lameduck.client.CancelReservationRequest cancelRequest = new lameduck.client.CancelReservationRequest();
                cancelRequest.setBookingNumber(f.getBookingNumber());
                cancelRequest.setCreditCard(lameDuckCreditCard);
                cancelRequest.setPrice(f.getPrice());
                getLameDuckPort().cancelReservation(cancelRequest);
            }
            f.setBookingState(BookingState.CANCELLED);
        }

        for (Hotel h : hotels) {
            if (h.getBookingState().equals(BookingState.BOOKED)) {
                niceview.client.CancelHotelRequest cancelRequest = new niceview.client.CancelHotelRequest();
                cancelRequest.setBookingNumber(h.getBookingNumber());
                getNiceViewPort().cancelHotel(cancelRequest);
            }
            h.setBookingState(BookingState.CANCELLED);
        }

        itinerary.setBookingState(BookingState.CANCELLED);
        itinerary.setPaymentStatus(PaymentStatus.CANCELLED);
        return itinerary;
    }

    public boolean addFlight(String bookingNumber, String flightBookingNumber) throws ItineraryException {
        Itinerary itinerary = getItinerary(bookingNumber);

        lameduck.client.GetFlightsRequest getFlightRequest = new lameduck.client.GetFlightsRequest();
        getFlightRequest.setBookingNumber(flightBookingNumber);
        lameduck.client.GetFlightsResponse getFlightResponse = lameDuckPort.getFlights(getFlightRequest);
        if (getFlightResponse.getFlights().getFlight().size() > 0) {
            lameduck.client.FlightType flightType = getFlightResponse.getFlights().getFlight().get(0);
            Flight flight = new Flight(flightType.getId(), flightType.getBookingNumber(), flightType.getCarrier(),
                    flightType.getFrom(), flightType.getTo(), DateUtils.toDate(flightType.getLiftOffDate()),
                    DateUtils.toDate(flightType.getLandingDate()), flightType.getPrice(), BookingState.IN_PROGRESS);
            itinerary.getFlights().add(flight);
            return true;
        }

        return false;
    }

    public boolean addHotel(String bookingNumber, String hotelBookingNumber) throws ItineraryException {
        Itinerary itinerary = getItinerary(bookingNumber);

        niceview.client.GetHotelsRequest getHotelRequest = new niceview.client.GetHotelsRequest();
        getHotelRequest.setBookingNumber(hotelBookingNumber);
        niceview.client.GetHotelsResponse getHotelResponse = niceViewPort.getHotels(getHotelRequest);
        if (getHotelResponse.getHotels().getHotel().size() > 0) {
            HotelType hotelType = getHotelResponse.getHotels().getHotel().get(0);
            AddressType addressType = hotelType.getAddress();
            Address address = new Address(addressType.getCity(), addressType.getStreet(), addressType.getZipCode());
            Hotel hotel = new Hotel(hotelType.getId(), hotelType.getBookingNumber(), hotelType.getName(), address, hotelType.getProvider(), hotelType.getPrice(), hotelType.isCreditCardGuarantee(), BookingState.IN_PROGRESS);
            itinerary.getHotels().add(hotel);
            return true;
        }

        return false;
    }
}

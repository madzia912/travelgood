package travelgood.bpel.test;

import dk.dtu.travelgood.AddFlightRequest;
import dk.dtu.travelgood.AddFlightResponse;
import dk.dtu.travelgood.AddHotelRequest;
import dk.dtu.travelgood.AddHotelResponse;
import dk.dtu.travelgood.BookItineraryFault_Exception;
import dk.dtu.travelgood.BookItineraryResponse;
import dk.dtu.travelgood.CancelItineraryFault_Exception;
import dk.dtu.travelgood.CancelItineraryResponse;
import dk.dtu.travelgood.CreateItineraryRequest;
import dk.dtu.travelgood.CreateItineraryResponse;
import dk.dtu.travelgood.GetFlightsRequest;
import dk.dtu.travelgood.GetFlightsResponse;
import dk.dtu.travelgood.GetHotelsResponse;
import dk.dtu.travelgood.GetItineraryRequest;
import dk.dtu.travelgood.GetItineraryResponse;
import dk.dtu.travelgood.commons.CreditCardType;
import dk.dtu.travelgood.commons.FlightType;
import dk.dtu.travelgood.commons.HotelType;
import dk.dtu.travelgood.commons.ItineraryType;
import java.text.ParseException;
import junit.framework.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import travelgood.utils.DateUtils;
import travelgood.utils.model.BookingState;

/**
 *
 * @author Stanislav Skuratovich
 */
public class TravelGoodBPELTest {

    @Test
    public void B() throws ParseException {
        // create itinerary
        CreateItineraryResponse cir = createItinerary("use1");
        Assert.assertTrue(cir != null);
        String bookingNumber = cir.getBookingNumber();

        // first flight
        GetFlightsResponse getFlightsResponse = getFlights(bookingNumber, "ABC", "CBA", "2014-10-10");

        Assert.assertTrue(getFlightsResponse != null && getFlightsResponse.getFlights() != null
                && getFlightsResponse.getFlights().getFlight() != null && getFlightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr1", getFlightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight1 = getFlightsResponse.getFlights().getFlight().get(0);
        addFlight(bookingNumber, flight1);

        //First hotel
        GetHotelsResponse getHotelsResponse = getHotels(bookingNumber, "Copenhagen", "2014-01-01", "2014-01-10");

        Assert.assertTrue(getHotelsResponse != null && getHotelsResponse.getHotels() != null
                && getHotelsResponse.getHotels().getHotel() != null && getHotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr2", getHotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel1 = getHotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel1);

        // second hotel
        getHotelsResponse = getHotels(bookingNumber, "Paris", "2014-01-01", "2014-01-10");

        Assert.assertTrue(getHotelsResponse != null && getHotelsResponse.getHotels() != null
                && getHotelsResponse.getHotels().getHotel() != null && getHotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr1", getHotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel2 = getHotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel2);

        //Get itinerary
        GetItineraryResponse getItineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(getItineraryResponse != null && getItineraryResponse.getItinerary() != null);

        ItineraryType itinerary = getItineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

        //Booking
        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Bech Camilla");
        creditCard.setExpMonth(7);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408822");

        try {
            BookItineraryResponse bookItinerary = bookItinerary(creditCard, bookingNumber);
            Assert.assertTrue(bookItinerary.isBooked());
        } catch (BookItineraryFault_Exception ex) {
            Assert.assertEquals("Book itinerary failed!", ex.getFaultInfo().getReason());
        }

        // checking after
        getItineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(getItineraryResponse != null && getItineraryResponse.getItinerary() != null);

        itinerary = getItineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

    }

    @Test
    public void P1() throws ParseException {
        // Create itinerary
        CreateItineraryResponse cir = createItinerary("use1");
        Assert.assertTrue(cir != null);
        String bookingNumber = cir.getBookingNumber();

        // First flight
        GetFlightsResponse flightsResponse = getFlights(bookingNumber, "ABC", "CBA", "2014-10-10");

        Assert.assertTrue(flightsResponse != null && flightsResponse.getFlights() != null
                && flightsResponse.getFlights().getFlight() != null && flightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight1 = flightsResponse.getFlights().getFlight().get(0);
        addFlight(bookingNumber, flight1);

        // First hotel
        GetHotelsResponse hotelsResponse = getHotels(bookingNumber, "Paris", "2014-01-01", "2014-01-10");

        Assert.assertTrue(hotelsResponse != null && hotelsResponse.getHotels() != null
                && hotelsResponse.getHotels().getHotel() != null && hotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel1 = hotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel1);

        // Second flight
        flightsResponse = getFlights(bookingNumber, "ASD", "DSA", "2014-10-12");

        Assert.assertTrue(flightsResponse != null && flightsResponse.getFlights() != null
                && flightsResponse.getFlights().getFlight() != null && flightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr2", flightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight2 = flightsResponse.getFlights().getFlight().get(0);
        addFlight(bookingNumber, flight2);

        // Third flight
        flightsResponse = getFlights(bookingNumber, "QWE", "EWQ", "2014-10-14");

        Assert.assertTrue(flightsResponse != null && flightsResponse.getFlights() != null
                && flightsResponse.getFlights().getFlight() != null && flightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr3", flightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight3 = flightsResponse.getFlights().getFlight().get(0);
        addFlight(bookingNumber, flight3);

        //Second hotel
        hotelsResponse = getHotels(bookingNumber, "Copenhagen", "2014-01-01", "2014-01-10");

        Assert.assertTrue(hotelsResponse != null && hotelsResponse.getHotels() != null
                && hotelsResponse.getHotels().getHotel() != null && hotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr2", hotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel2 = hotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel2);

        //Get itinerary
        GetItineraryResponse itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        ItineraryType itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getFlights().getFlight().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getFlights().getFlight().get(1).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getFlights().getFlight().get(2).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getFlights().getFlight().get(2).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());
    }

    @Test
    public void P2() throws ParseException, CancelItineraryFault_Exception {
        // Create itinerary
        CreateItineraryResponse cir = createItinerary("use1");
        Assert.assertTrue(cir != null);
        String bookingNumber = cir.getBookingNumber();

        // First flight
        GetFlightsResponse flightsResponse = getFlights(bookingNumber, "ABC", "CBA", "2014-10-10");

        Assert.assertTrue(flightsResponse != null && flightsResponse.getFlights() != null
                && flightsResponse.getFlights().getFlight() != null && flightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight1 = flightsResponse.getFlights().getFlight().get(0);
        addFlight(bookingNumber, flight1);

        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Bech Camilla");
        creditCard.setExpMonth(7);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408822");

        cancelItinerary(creditCard, bookingNumber);
    }

    @Test
    public void C1() throws ParseException {
        // Create itinerary
        CreateItineraryResponse cir = createItinerary("use1");
        Assert.assertTrue(cir != null);
        String bookingNumber = cir.getBookingNumber();

        // First flight
        GetFlightsResponse flightsResponse = getFlights(bookingNumber, "ABC", "CBA", "2014-10-10");

        Assert.assertTrue(flightsResponse != null && flightsResponse.getFlights() != null
                && flightsResponse.getFlights().getFlight() != null && flightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight1 = flightsResponse.getFlights().getFlight().get(0);
        AddFlightResponse addFlight = addFlight(bookingNumber, flight1);

        Assert.assertTrue(addFlight.isAdded());

        //First hotel
        GetHotelsResponse hotelsResponse = getHotels(bookingNumber, "Paris", "2014-01-01", "2014-01-10");

        Assert.assertTrue(hotelsResponse != null && hotelsResponse.getHotels() != null
                && hotelsResponse.getHotels().getHotel() != null && hotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel1 = hotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel1);

        //Second hotel
        hotelsResponse = getHotels(bookingNumber, "London", "2014-01-01", "2014-01-10");

        Assert.assertTrue(hotelsResponse != null && hotelsResponse.getHotels() != null
                && hotelsResponse.getHotels().getHotel() != null && hotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr3", hotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel2 = hotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel2);

        //Get itinerary
        GetItineraryResponse itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        ItineraryType itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

        //Booking
        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Anne Strandberg");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408816");

        try {
            BookItineraryResponse bookItinerary = bookItinerary(creditCard, bookingNumber);
            Assert.assertTrue(bookItinerary.isBooked());
        } catch (BookItineraryFault_Exception ex) {
            Assert.fail(ex.getMessage());
        }

        //Check itinerary again
        itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

        // Cancel
        try {
            CancelItineraryResponse cancelItinerary = cancelItinerary(creditCard, bookingNumber);
            Assert.assertTrue(cancelItinerary.isCanceled());
        } catch (CancelItineraryFault_Exception ex) {
            Assert.fail(ex.getMessage());
        }

        //Check itinerary again
        itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());
    }

    @Test
    public void C2() throws ParseException {
        // Create itinerary
        CreateItineraryResponse cir = createItinerary("use1");
        Assert.assertTrue(cir != null);
        String bookingNumber = cir.getBookingNumber();

        // First flight
        GetFlightsResponse flightsResponse = getFlights(bookingNumber, "ABC", "CBA", "2014-10-10");
        Assert.assertTrue(flightsResponse != null && flightsResponse.getFlights() != null
                && flightsResponse.getFlights().getFlight() != null && flightsResponse.getFlights().getFlight().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlight().get(0).getBookingNumber());

        FlightType flight1 = flightsResponse.getFlights().getFlight().get(0);
        addFlight(bookingNumber, flight1);

        //First hotel
        GetHotelsResponse hotelsResponse = getHotels(bookingNumber, "New York", "2014-01-01", "2014-01-10");

        Assert.assertTrue(hotelsResponse != null && hotelsResponse.getHotels() != null
                && hotelsResponse.getHotels().getHotel() != null && hotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr4", hotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel1 = hotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel1);

        //Second hotel
        hotelsResponse = getHotels(bookingNumber, "Paris", "2014-01-01", "2014-01-10");

        Assert.assertTrue(hotelsResponse != null && hotelsResponse.getHotels() != null
                && hotelsResponse.getHotels().getHotel() != null && hotelsResponse.getHotels().getHotel().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotel().get(0).getBookingNumber());

        HotelType hotel2 = hotelsResponse.getHotels().getHotel().get(0);
        addHotel(bookingNumber, hotel2);

        //Get itinerary
        GetItineraryResponse itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        ItineraryType itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr4", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

        //Booking
        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Anne Strandberg");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408816");

        try {
            BookItineraryResponse bookItinerary = bookItinerary(creditCard, bookingNumber);
            Assert.assertTrue(bookItinerary.isBooked());
        } catch (BookItineraryFault_Exception ex) {
            Assert.fail(ex.getMessage());
        }

        //Check itinerary again
        itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr4", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

        // Cancel
        try {
            CancelItineraryResponse cancelItinerary = cancelItinerary(creditCard, bookingNumber);
            Assert.assertTrue(cancelItinerary.isCanceled());
        } catch (CancelItineraryFault_Exception ex) {
            Assert.assertEquals("Failed to cancel the trip!", ex.getFaultInfo().getReason());
        }

        //Check itinerary again
        itineraryResponse = getItinerary(bookingNumber);
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights().getFlight()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels().getHotel()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().getFlight().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED.toString(), itinerary.getFlights().getFlight().get(0).getBookingState());
        Assert.assertEquals("bookingNr4", itinerary.getHotels().getHotel().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getHotels().getHotel().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().getHotel().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED.toString(), itinerary.getHotels().getHotel().get(1).getBookingState());

    }

    private static AddFlightResponse addFlight(String bookingNumber, FlightType flight) {
        AddFlightRequest addFlightRequestPart = new AddFlightRequest();
        addFlightRequestPart.setBookingNumber(bookingNumber);
        addFlightRequestPart.setFlight(flight);

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.addFlight(addFlightRequestPart);
    }

    private static AddHotelResponse addHotel(String bookingNumber, HotelType hotel) {
        AddHotelRequest addHotelRequestPart = new AddHotelRequest();
        addHotelRequestPart.setHotel(hotel);
        addHotelRequestPart.setBookingNumber(bookingNumber);

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.addHotel(addHotelRequestPart);
    }

    private static BookItineraryResponse bookItinerary(CreditCardType creditCard, String bookingNumber) throws BookItineraryFault_Exception {
        dk.dtu.travelgood.BookItineraryRequest bookItineraryRequestPart = new dk.dtu.travelgood.BookItineraryRequest();
        bookItineraryRequestPart.setBookingNumber(bookingNumber);
        bookItineraryRequestPart.setCreditCard(creditCard);

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.bookItinerary(bookItineraryRequestPart);
    }

    private static CancelItineraryResponse cancelItinerary(CreditCardType creditCard, String bookingNumber) throws CancelItineraryFault_Exception {
        dk.dtu.travelgood.CancelItineraryRequest cancelItineraryRequestPart = new dk.dtu.travelgood.CancelItineraryRequest();
        cancelItineraryRequestPart.setBookingNumber(bookingNumber);
        cancelItineraryRequestPart.setCreditCard(creditCard);

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.cancelItinerary(cancelItineraryRequestPart);
    }

    private static CreateItineraryResponse createItinerary(String userID) {
        dk.dtu.travelgood.CreateItineraryRequest createItineraryRequestPart = new CreateItineraryRequest();
        createItineraryRequestPart.setUserId(userID);

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.createItinerary(createItineraryRequestPart);
    }

    private static GetFlightsResponse getFlights(String bookingNumber, String from, String to, String date) throws ParseException {
        dk.dtu.travelgood.GetFlightsRequest getFlightsRequestPart = new GetFlightsRequest();
        getFlightsRequestPart.setBookingNumber(bookingNumber);
        getFlightsRequestPart.setFrom(from);
        getFlightsRequestPart.setTo(to);
        getFlightsRequestPart.setTakeOffDate(DateUtils.toXmlGregorianCalendar(date));

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getFlights(getFlightsRequestPart);
    }

    private static GetHotelsResponse getHotels(String bookingNumber, String city, String arrival, String departure) throws ParseException {
        dk.dtu.travelgood.GetHotelsRequest getHotelsRequestPart = new dk.dtu.travelgood.GetHotelsRequest();

        getHotelsRequestPart.setBookingNumber(bookingNumber);
        getHotelsRequestPart.setCity(city);
        getHotelsRequestPart.setArrivalDate(DateUtils.toXmlGregorianCalendar(arrival));
        getHotelsRequestPart.setDepartureDate(DateUtils.toXmlGregorianCalendar(departure));

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getHotels(getHotelsRequestPart);
    }

    private static GetItineraryResponse getItinerary(String bookingNumber) {
        GetItineraryRequest getItineraryRequestPart = new GetItineraryRequest();
        getItineraryRequestPart.setBookingNumber(bookingNumber);

        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getItinerary(getItineraryRequestPart);
    }
}

package travelgood.bpel.test;

import dk.dtu.travelgood.AddFlightResponse;
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
import dk.dtu.travelgood.GetItineraryResponse;
import dk.dtu.travelgood.commons.FlightType;
import dk.dtu.travelgood.commons.HotelType;
import dk.dtu.travelgood.commons.ItineraryType;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;

/**
 *
 * @author Jacob
 */
public class TravelGoodBPELTest {

    @Test
    public void CoolP1() {
        String bookingNumber = createItinerary("use1");
        
        //GetFlightsResponse gfResponse = getFlights()
        
        //createItinerary(null)
    }
    

    @Test
    public void P1() {
        // Crate itinerary
        ClientResponse createItinerary = createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2014-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        // First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        // Second flight
        flightsResponse = c.getFlights(cir.getBookingNumber(), "ASD", "DSA", "2014-10-12");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr2", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight2 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight2.getBookingNumber());

        // Third flight
        flightsResponse = c.getFlights(cir.getBookingNumber(), "QWE", "EWQ", "2014-10-14");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr3", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight3 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight3.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "Copenhagen", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr2", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel2 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel2.getBookingNumber());

        //Get itinerary
        GetItineraryResponse itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        Itinerary itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getFlights().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getFlights().get(1).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getFlights().get(2).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getFlights().get(2).getBookingState());

        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(1).getBookingState());
    }
    /*

    @Test
    public void P2() {
        // Crate itinerary
        ClientResponse createItinerary = c.createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2014-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        CreditCard creditCard = new CreditCard();
        creditCard.setName("Bech Camilla");
        creditCard.setExpMonth(7);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408822");

        c.cancelItinerary(creditCard, cir.getBookingNumber());
    }

    @Test
    public void B() {
        // Crate itinerary
        ClientResponse createItinerary = c.createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2014-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        //First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "Copenhagen", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr2", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel2 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel2.getBookingNumber());

        //Get itinerary
        GetItineraryResponse itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        Itinerary itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(1).getBookingState());

        //Booking
        CreditCard creditCard = new CreditCard();
        creditCard.setName("Bech Camilla");
        creditCard.setExpMonth(7);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408822");
        
        try {
            ClientResponse bookItinerary = c.bookItinerary(creditCard, cir.getBookingNumber());
            if (Status.OK == bookItinerary.getClientResponseStatus()) {
                Assert.fail();
            }
        } catch (UniformInterfaceException ex) {
            Assert.assertEquals(Response.Status.NOT_ACCEPTABLE, ex.getResponse().getClientResponseStatus());
        }

        //Check itinerary again
        itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr2", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(1).getBookingState());
    }

    @Test
    public void C1() {
        // Crate itinerary
        ClientResponse createItinerary = c.createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2014-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        //First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "London", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr3", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel2 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel2.getBookingNumber());

        //Get itinerary
        GetItineraryResponse itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        Itinerary itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(1).getBookingState());

        //Booking
        CreditCard creditCard = new CreditCard();
        creditCard.setName("Anne Strandberg");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408816");

        try {
            ClientResponse bookItinerary = c.bookItinerary(creditCard, cir.getBookingNumber());
            Assert.assertEquals(Response.Status.OK.getStatusCode(), bookItinerary.getClientResponseStatus().getStatusCode());
        } catch (UniformInterfaceException ex) {
            Assert.fail(ex.getMessage());
        }

        //Check itinerary again
        itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getHotels().get(1).getBookingState());

        // Cancell
        try {
            ClientResponse cancelItinerary = c.cancelItinerary(creditCard, cir.getBookingNumber());
            Assert.assertEquals(Response.Status.OK.getStatusCode(), cancelItinerary.getClientResponseStatus().getStatusCode());
        } catch (UniformInterfaceException ex) {
            Assert.fail(ex.getMessage());
        }

        //Check itinerary again
        itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr3", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED, itinerary.getHotels().get(1).getBookingState());
    }

    @Test
    public void C2() {
        // Crate itinerary
        ClientResponse createItinerary = c.createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2014-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        //First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "New York", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr4", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2014-01-01", "2014-01-10");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel2 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel2.getBookingNumber());

        //Get itinerary
        GetItineraryResponse itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        Itinerary itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr4", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.IN_PROGRESS, itinerary.getHotels().get(1).getBookingState());

        //Booking
        CreditCard creditCard = new CreditCard();
        creditCard.setName("Anne Strandberg");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408816");

        try {
            ClientResponse bookItinerary = c.bookItinerary(creditCard, cir.getBookingNumber());
            Assert.assertEquals(Response.Status.OK.getStatusCode(), bookItinerary.getClientResponseStatus().getStatusCode());
        } catch (UniformInterfaceException ex) {
            Assert.fail(ex.getMessage());
        }

        //Check itinerary again
        itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr4", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getHotels().get(1).getBookingState());

        // Cancel
        try {
            ClientResponse cancelItinerary = c.cancelItinerary(creditCard, cir.getBookingNumber());
            if (Status.OK == cancelItinerary.getClientResponseStatus()) {
                Assert.fail();
            }
        } catch (UniformInterfaceException ex) {
            Assert.assertEquals(Response.Status.NOT_ACCEPTABLE, ex.getResponse().getClientResponseStatus());
        }

        //Check itinerary again
        itineraryResponse = c.getItinerary(cir.getBookingNumber());
        Assert.assertTrue(itineraryResponse != null && itineraryResponse.getItinerary() != null);

        itinerary = itineraryResponse.getItinerary();

        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getFlights()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(itinerary.getHotels()));

        Assert.assertEquals("bookingNr1", itinerary.getFlights().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.CANCELLED, itinerary.getFlights().get(0).getBookingState());
        Assert.assertEquals("bookingNr4", itinerary.getHotels().get(0).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getHotels().get(0).getBookingState());
        Assert.assertEquals("bookingNr1", itinerary.getHotels().get(1).getBookingNumber());
        Assert.assertEquals(BookingState.BOOKED, itinerary.getHotels().get(1).getBookingState());
    }
 */
    
    private static boolean addFlight(dk.dtu.travelgood.AddFlightRequest addFlightRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.addFlight(addFlightRequestPart).isAdded();
    }

    private static boolean addHotel(dk.dtu.travelgood.AddHotelRequest addHotelRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.addHotel(addHotelRequestPart).isAdded();
    }

    private static boolean bookItinerary(dk.dtu.travelgood.BookItineraryRequest bookItineraryRequestPart) throws BookItineraryFault_Exception {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.bookItinerary(bookItineraryRequestPart).isBooked();
    }

    private static boolean cancelItinerary(dk.dtu.travelgood.CancelItineraryRequest cancelItineraryRequestPart) throws CancelItineraryFault_Exception {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.cancelItinerary(cancelItineraryRequestPart).isCanceled();
    }

    private static String createItinerary(String userID) {
        dk.dtu.travelgood.CreateItineraryRequest createItineraryRequestPart = new CreateItineraryRequest();
        
        createItineraryRequestPart.setUserId(userID);
        
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.createItinerary(createItineraryRequestPart).getBookingNumber();
    }

    //private static List<FlightType> getFlights(dk.dtu.travelgood.GetFlightsRequest getFlightsRequestPart) {
    private static List<FlightType> getFlights(String bookingNumber, String from, String to, String date) {
        dk.dtu.travelgood.GetFlightsRequest getFlightsRequestPart = new GetFlightsRequest();
        
        getFlightsRequestPart.setBookingNumber(bookingNumber);
        getFlightsRequestPart.setFrom(from);
        getFlightsRequestPart.setTo(to);
        
        
        getFlightsRequestPart.setTakeOffDate(null);
        
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getFlights(getFlightsRequestPart).getFlights().getFlight();
    }

    private static List<HotelType> getHotels(dk.dtu.travelgood.GetHotelsRequest getHotelsRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getHotels(getHotelsRequestPart).getHotels().getHotel();
    }
    
    private static ItineraryType getItinerary(dk.dtu.travelgood.GetItineraryRequest getItineraryRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getItinerary(getItineraryRequestPart).getItinerary();
    }
}

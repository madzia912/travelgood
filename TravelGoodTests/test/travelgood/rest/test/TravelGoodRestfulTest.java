package travelgood.rest.test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import travelgood.rest.TravelGoodRestClient;
import travelgood.utils.model.BookingState;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Flight;
import travelgood.utils.model.Hotel;
import travelgood.utils.model.Itinerary;
import travelgood.utils.model.rest.CreateItineraryResponse;
import travelgood.utils.model.rest.GetFlightsResponse;
import travelgood.utils.model.rest.GetHotelsResponse;
import travelgood.utils.model.rest.GetItineraryResponse;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class TravelGoodRestfulTest {

    private TravelGoodRestClient c = new TravelGoodRestClient();

    @Test
    public void P1() {
        // Crate itinerary
        ClientResponse createItinerary = c.createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2012-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        // First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2012-10-10", "2012-10-15");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        // Second flight
        flightsResponse = c.getFlights(cir.getBookingNumber(), "ASD", "DSA", "2012-10-12");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr2", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight2 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight2.getBookingNumber());

        // Third flight
        flightsResponse = c.getFlights(cir.getBookingNumber(), "QWE", "EWQ", "2012-10-14");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr3", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight3 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight3.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "Copenhagen", "2012-10-14", "2012-10-15");
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

    @Test
    public void P2() {
        // Crate itinerary
        ClientResponse createItinerary = c.createItinerary("use1");
        CreateItineraryResponse cir = createItinerary.getEntity(CreateItineraryResponse.class);
        Assert.assertTrue(cir != null);

        // First flight
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2012-10-10");
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
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2012-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        //First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "Copenhagen", "2012-10-14", "2012-10-15");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr2", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2012-10-10", "2012-10-15");
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
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2012-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        //First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2012-10-10", "2012-10-15");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr1", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "London", "2012-10-10", "2012-10-15");
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
        GetFlightsResponse flightsResponse = c.getFlights(cir.getBookingNumber(), "ABC", "CBA", "2012-10-10");
        Assert.assertTrue(flightsResponse.getFlights() != null && flightsResponse.getFlights().getFlights() != null && flightsResponse.getFlights().getFlights().size() == 1);
        Assert.assertEquals("bookingNr1", flightsResponse.getFlights().getFlights().get(0).getBookingNumber());

        Flight flight1 = flightsResponse.getFlights().getFlights().get(0);
        c.addFlight(cir.getBookingNumber(), flight1.getBookingNumber());

        //First hotel
        GetHotelsResponse hotelsResponse = c.getHotels(cir.getBookingNumber(), "New York", "2012-10-10", "2012-10-15");
        Assert.assertTrue(hotelsResponse.getHotels() != null && hotelsResponse.getHotels().getHotels() != null && hotelsResponse.getHotels().getHotels().size() == 1);
        Assert.assertEquals("bookingNr4", hotelsResponse.getHotels().getHotels().get(0).getBookingNumber());

        Hotel hotel1 = hotelsResponse.getHotels().getHotels().get(0);
        c.addHotel(cir.getBookingNumber(), hotel1.getBookingNumber());

        //Second hotel
        hotelsResponse = c.getHotels(cir.getBookingNumber(), "Paris", "2012-10-10", "2012-10-15");
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
}

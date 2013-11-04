package lameduck.test;

import junit.framework.Assert;
import lameduck.test.client.BookFlightFault_Exception;
import lameduck.test.client.BookFlightRequest;
import lameduck.test.client.BookFlightResponse;
import lameduck.test.client.CancelReservationFault_Exception;
import lameduck.test.client.CancelReservationRequest;
import lameduck.test.client.CancelReservationResponse;
import lameduck.test.client.CreditCardType;
import lameduck.test.client.FlightType;
import lameduck.test.client.GetFlightsRequest;
import lameduck.test.client.GetFlightsResponse;
import lameduck.test.client.LameDuckPortType;
import lameduck.test.client.LameDuckService;
import org.junit.Test;
import travelgood.utils.DateUtils;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class LameDuckTest {
    
    private LameDuckPortType port  = new LameDuckService().getLameDuckPort();

    private LameDuckPortType getPort() {
        return port;
    }

    @Test
    public void testGetFlights1() {
        GetFlightsRequest request = new GetFlightsRequest();
        request.setTakeOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetFlightsResponse response = getPort().getFlights(request);

        Assert.assertTrue(response != null && response.getFlights() != null && response.getFlights().getFlight() != null);
        Assert.assertTrue(response.getFlights().getFlight().size() > 0);
    }

    @Test
    public void testGetFlights2() {
        GetFlightsRequest request = new GetFlightsRequest();
        request.setFrom("ABC");
        request.setTo("CBA");
        request.setTakeOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetFlightsResponse response = getPort().getFlights(request);

        Assert.assertTrue(response != null && response.getFlights() != null && response.getFlights().getFlight() != null);
        Assert.assertTrue(response.getFlights().getFlight().size() == 1);
        Assert.assertEquals("flight1", response.getFlights().getFlight().get(0).getId());
    }

    @Test
    public void testBookAndCancel() throws BookFlightFault_Exception, CancelReservationFault_Exception {
        GetFlightsRequest request = new GetFlightsRequest();
        request.setFrom("ABC");
        request.setTo("CBA");
        request.setTakeOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetFlightsResponse response = getPort().getFlights(request);

        Assert.assertTrue(response != null && response.getFlights() != null && response.getFlights().getFlight() != null && response.getFlights().getFlight().size() > 0);
        
        FlightType flight = response.getFlights().getFlight().get(0);
        
        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Anne Strandberg");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408816");

        BookFlightRequest bookRequest = new BookFlightRequest();
        bookRequest.setBookingNumber(flight.getBookingNumber());
        bookRequest.setCreditCard(creditCard);

        BookFlightResponse bookResponse = getPort().bookFlight(bookRequest);

        Assert.assertTrue(bookResponse.isBooked());
        
        CancelReservationRequest cancelRequest = new CancelReservationRequest();
        cancelRequest.setBookingNumber(flight.getBookingNumber());
        cancelRequest.setCreditCard(creditCard);
        cancelRequest.setPrice(flight.getPrice());
        
        CancelReservationResponse cancelResponse = getPort().cancelReservation(cancelRequest);
        
        Assert.assertTrue(cancelResponse.isCanceled());
    }
    
    @Test(expected=BookFlightFault_Exception.class)
    public void testFailBook1() throws BookFlightFault_Exception {
        BookFlightRequest bookRequest = new BookFlightRequest();
        bookRequest.setBookingNumber("SOME_INVALID_AND_FAKE_AND_WRONG_FLIGHT_ID");
        getPort().bookFlight(bookRequest);
    }
    
    @Test(expected=BookFlightFault_Exception.class)
    public void testFailBook2() throws BookFlightFault_Exception {
        BookFlightRequest bookRequest = new BookFlightRequest();
        bookRequest.setBookingNumber("flight5");
        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Thor-Jensen Claus");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408825");
        bookRequest.setCreditCard(creditCard);
        
        getPort().bookFlight(bookRequest);
    }
    
    @Test(expected=CancelReservationFault_Exception.class)
    public void testFailCancel() throws BookFlightFault_Exception, CancelReservationFault_Exception {
        CancelReservationRequest cancelRequest = new CancelReservationRequest();
        cancelRequest.setBookingNumber("SOME_INVALID_AND_FAKE_AND_WRONG_RESERVATION_ID");
        getPort().cancelReservation(cancelRequest);
    }
}

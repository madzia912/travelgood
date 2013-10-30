package lameduck.test;

import junit.framework.Assert;
import lameduck.test.client.BookFlightFault_Exception;
import lameduck.test.client.BookFlightRequest;
import lameduck.test.client.BookFlightResponse;
import lameduck.test.client.CancelReservationFault_Exception;
import lameduck.test.client.CancelReservationRequest;
import lameduck.test.client.CancelReservationResponse;
import lameduck.test.client.FlightType;
import lameduck.test.client.GetFlightsRequest;
import lameduck.test.client.GetFlightsResponse;
import lameduck.test.client.LameDuckPortType;
import lameduck.test.client.LameDuckService;
import org.apache.commons.lang3.StringUtils;
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
        request.setDateFrom(1333118534);
        request.setDateTo(1386118534);

        GetFlightsResponse response = getPort().getFlights(request);

        Assert.assertTrue(response != null && response.getFlights() != null && response.getFlights().getFlight() != null);
        Assert.assertTrue(response.getFlights().getFlight().size() > 0);
    }

    @Test
    public void testGetFlights2() {
        GetFlightsRequest request = new GetFlightsRequest();
        request.setFrom("ABC");
        request.setTo("CBA");
        request.setDateFrom(DateUtils.getTimestamp(2013, 10, 10));
        request.setDateTo(DateUtils.getTimestamp(2013, 10, 20));

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
        request.setDateFrom(1333118534);
        request.setDateTo(1386118534);

        GetFlightsResponse response = getPort().getFlights(request);

        FlightType flight = response.getFlights().getFlight().get(0);

        BookFlightRequest bookRequest = new BookFlightRequest();
        bookRequest.setFlightId(flight.getId());
        bookRequest.setPlaces(2);

        BookFlightResponse bookResponse = getPort().bookFlight(bookRequest);

        Assert.assertTrue(StringUtils.isNotBlank(bookResponse.getReservationId()));
        Assert.assertEquals(200, bookResponse.getPrice());
        
        CancelReservationRequest cancelRequest = new CancelReservationRequest();
        cancelRequest.setReservationId(bookResponse.getReservationId());
        
        CancelReservationResponse cancelResponse = getPort().cancelReservation(cancelRequest);
        
        Assert.assertEquals("Cancelled", cancelResponse.getStatus());
    }
    
    @Test(expected=BookFlightFault_Exception.class)
    public void testFailBook1() throws BookFlightFault_Exception {
        GetFlightsRequest request = new GetFlightsRequest();
        request.setFrom("ABC");
        request.setTo("CBA");
        request.setDateFrom(1333118534);
        request.setDateTo(1386118534);

        GetFlightsResponse response = getPort().getFlights(request);

        FlightType flight = response.getFlights().getFlight().get(0);

        BookFlightRequest bookRequest = new BookFlightRequest();
        bookRequest.setFlightId(flight.getId());
        bookRequest.setPlaces(150);
        
        getPort().bookFlight(bookRequest);
    }
    
    @Test(expected=BookFlightFault_Exception.class)
    public void testFailBook2() throws BookFlightFault_Exception {
        BookFlightRequest bookRequest = new BookFlightRequest();
        bookRequest.setFlightId("SOME_INVALID_AND_FAKE_AND_WRONG_FLIGHT_ID");
        bookRequest.setPlaces(150);
        
        getPort().bookFlight(bookRequest);
    }
    
    @Test(expected=CancelReservationFault_Exception.class)
    public void testFailCancel() throws BookFlightFault_Exception, CancelReservationFault_Exception {
        CancelReservationRequest cancelRequest = new CancelReservationRequest();
        cancelRequest.setReservationId("SOME_INVALID_AND_FAKE_AND_WRONG_RESERVATION_ID");
        
        getPort().cancelReservation(cancelRequest);
    }
}

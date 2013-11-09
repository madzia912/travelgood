package travelgood.bpel.test;

import static org.junit.Assert.*;
import org.junit.Test;
import travelgood.utils.DateUtils;

/**
 *
 * @author Jacob
 */
public class TravelGoodBPELTest {

    public TravelGoodBPELTest() {
    }
    
    @Test
    public void testCreateItinerary() {
        String bookingNumber = createItinerary("Cow");
        
        if(true){}
    }
    
    /*
    @Test
    public void getFlights() {
        GetFlightsRequest request = new GetFlightsRequest();

        request.setFrom("ABC");
        request.setTo("CBA");
        request.setTakeOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetFlightsResponse response = getFlights(request);

        assertTrue(response.getFlights().getFlight().size() == 1);
    }

    @Test
    public void getHotels() {
        GetHotelsRequest request = new GetHotelsRequest();

        request.setCity("Paris");
        request.setArrivalDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetHotelsResponse response = getHotels(request);

        assertTrue(response.getHotels().getHotel().get(0).getId().equals("hotel1"));
    }

    private static GetFlightsResponse getFlights(dk.dtu.travelgood.GetFlightsRequest getFlightsRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getFlights(getFlightsRequestPart);
    }

    private static GetHotelsResponse getHotels(dk.dtu.travelgood.GetHotelsRequest getHotelsRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getHotels(getHotelsRequestPart);
    }*/

    private static String createItinerary(java.lang.String userId) {
        dk.dtu.travelgood.TravelGoodRPCService service = new dk.dtu.travelgood.TravelGoodRPCService();
        dk.dtu.travelgood.TravelGoodRPCPortType port = service.getTravelGoodRPCPort();
        return port.createItinerary(userId);
    }   
}
